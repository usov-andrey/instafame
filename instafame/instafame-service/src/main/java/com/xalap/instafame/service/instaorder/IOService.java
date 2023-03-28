/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

import com.xalap.crm.service.contact.ContactDataService;
import com.xalap.crm.service.contact.ContactDataType;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.lead.LeadService;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.order.orderline.OrderLineBean;
import com.xalap.crm.service.order.orderline.OrderLineService;
import com.xalap.crm.service.order.product.OrderProductBean;
import com.xalap.crm.service.order.product.OrderProductService;
import com.xalap.crm.service.scheduler.SchedulerService;
import com.xalap.framework.data.CrudService;
import com.xalap.framework.exception.ClientErrorException;
import com.xalap.framework.notification.NotificationService;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.RandomUtils;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.instaorder.parser.OrderLineParser;
import com.xalap.instafame.service.instaorder.task.CreateCommentTasks;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskStatus;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;
import com.xalap.instafame.service.instaorder.task.provider.MemoryIOTaskProvider;
import com.xalap.instagram.service.account.InstagramAccountProvider;
import com.xalap.instagram.service.api.InstagramClient;
import com.xalap.instagram.service.user.InstagramUserBean;
import com.xalap.instagram.service.user.InstagramUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * Логика работы:
 * Система оставляет заказ. Далее каждый день в зависимости от того, что нужно исполнить в этом заказе система
 * производит определенные действия.
 *
 * @author Усов Андрей
 * @since 06/01/2019
 */
@Service
public class IOService extends CrudService<IOBean, IORepository, Integer> {

    private static final Logger log = LoggerFactory.getLogger(IOService.class);
    private static final String APP_ROOT_URL = "http://instafame.xalap/ui/";

    @Autowired
    private InstagramUserService userService;
    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private IOTaskService ioTaskService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private OrderLineParser orderLineParser;
    @Autowired
    private LeadService leadService;
    @Autowired
    private ContactDataService contactDataService;
    @Autowired
    private InstagramAccountProvider accountProvider;

    public IOService() {
        super();
    }


    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        schedulerService.register(ProcessInstaOrdersTask.class, "Обработка заказов");
        schedulerService.register(IOStopsNotificationTask.class, "Уведомления при задержке заказов");
    }

    /**
     * @return Максимальное время остановки хода выполнения заказа(часов)
     */
    public int getMaxDelayHours() {
        return 14;
    }

    /**
     * Есть уже какой-то начатый заказ на указанный instagram или нет
     */
    public boolean existsStartedOrder(InstagramUserBean user) {
        return !getRepository().findByUser(user).isEmpty();
    }

    public void updateOrdersCost() {
        Map<OrderBean, List<IOBean>> orderMap = new HashMap<>();
        List<IOBean> instaOrders = getAll();
        MemoryIOTaskProvider memoryIOTaskProvider = ioTaskService.memoryTaskProviderAll();
        for (IOBean instaOrder : instaOrders) {
            if (instaOrder.getOrder() != null) {
                CollectionHelper.getArrayListOrCreate(orderMap, instaOrder.getOrder()).add(instaOrder);
            }
        }
        Date date = DateHelper.getDateTime("15.05.2019 00:00");
        for (Map.Entry<OrderBean, List<IOBean>> orderBeanListEntry : orderMap.entrySet()) {
            OrderBean orderBean = orderBeanListEntry.getKey();
            if (orderBean.getCreateTime().after(date)) {
                List<IOBean> instaOrderBeans = orderMap.get(orderBean);
                updateOrderCost(orderBean, instaOrderBeans, memoryIOTaskProvider);
            }
        }
    }

    public List<IOBean> getNotCompleted() {
        return repository().findByStatusNot(IOStatus.Completed);
    }

    public List<IOBean> getNotCreated() {
        return repository().findByStatusNot(IOStatus.Created);
    }

    public List<IOBean> getCompleted() {
        return repository().findByStatus(IOStatus.Completed);
    }

    /**
     * Переводим заказ в статус Оплачено
     */
    public void payOrder(OrderBean orderBean) {
        changeStatus(getByOrder(orderBean), IOStatus.Created, IOStatus.Payed);
    }

    /**
     * @deprecated
     */
    public void confirmSpeed(OrderBean orderBean, IOFollowersSpeed followersSpeed) {
        List<IOBean> byOrder = getByOrder(orderBean);
        for (IOBean ioBean : byOrder) {
            if (ioBean.getOrderStrategy() != followersSpeed) {
                ioBean.setOrderStrategy(followersSpeed);
                save(ioBean);
            }
        }
    }

    /**
     * Переводим заказ в статус Подтверждено
     */
    public void confirmOrder(OrderBean orderBean) {
        List<IOBean> byOrder = getByOrder(orderBean);
        changeStatus(byOrder, IOStatus.Payed, IOStatus.Confirmed);
    }

    private void changeStatus(List<IOBean> instaOrders, IOStatus from, IOStatus to) {
        for (IOBean instaOrderBean : instaOrders) {
            if (instaOrderBean.getStatus() == from) {
                instaOrderBean.setStatus(to);
                save(instaOrderBean);
            }
        }
    }

    public List<IOBean> getByOrder(OrderBean orderBean) {
        return repository().findByOrder(orderBean);
    }

    /**
     * Начать выполнять заказ
     */
    public void startOrder(IOBean instaOrderBean) {
        if (instaOrderBean.getStatus() == IOStatus.Payed || instaOrderBean.getStatus() == IOStatus.Confirmed) {
            InstagramUserBean user = instaOrderBean.getUser();
            if (user != null && user.isPrivate()) {
                instaOrderBean.setStatus(IOStatus.UserChangedToPrivate);
                notificationService.sendMessage("Пользователь " + user.getUserName() + " сделал заказ, но у него закрытый аккаунт:" +
                        user.accountUrl());
            } else {
                MemoryIOTaskProvider taskProvider = ioTaskService.memoryTaskProvider(instaOrderBean);
                ioTaskService.addAutoTasks(instaOrderBean, taskProvider);
                ioTaskService.runTasks(instaOrderBean, taskProvider);
                instaOrderBean.setStatus(IOStatus.InProgress);
            }
            save(instaOrderBean);
            orderService.runOrder(instaOrderBean.getOrder());
        }
    }

    /**
     * Обновлять информацию о пользователе нужно раз в час в случае
     * 1. Наличия задач в inProgress
     * 2. В случае невыполненного заказа по лайкам или коммментариям(при появлении новой публикации отправляем уведомление)
     * Иначе обновляем раз в 12 часов
     *
     */
    private Optional<InstagramUserBean> updateInstagramUser(IOBean instaOrderBean, IOTaskProvider taskProvider) {
        boolean inProgressHaveTasks = !taskProvider.getTasks(instaOrderBean, InstaOrderTaskStatus.InProgress).isEmpty();
        boolean needMoreLikesOrComments = needMoreLikesOrComments(instaOrderBean, taskProvider);
        int minutesToUpdate = 60 * (needMoreLikesOrComments ? 1 :
                inProgressHaveTasks ? RandomUtils.nextInt(3) + 3 ://В случае, если измеряем только подписчиков, то обновляем раз в
                        24);
        Optional<InstagramUserBean> userBean = userService.updateUserWithLastMediaEveryMinutes(instaOrderBean.getUser(), minutesToUpdate);
        if (needMoreLikesOrComments
                && userBean.isPresent() //Пользователь может удалить свой аккаунт при проверке
                && userBean.get().getUserStats().getMediaCount() > instaOrderBean.getUser().getUserStats().getMediaCount()) {
            notificationService.sendMessage("Пользователь " + userBean.get().getUserName() + " добавил публикацию");
        }
        return userBean;
    }

    private boolean needMoreLikesOrComments(IOBean instaOrderBean, IOTaskProvider taskProvider) {
        IOStats instaOrderStats = new IOStats(instaOrderBean, taskProvider.getTasks(instaOrderBean));
        //Если в заказе не было лайков, комментариев или они все выданы, то возвращаем false
        return instaOrderStats.needMoreLikesOrComments(instaOrderBean);
    }

    public void updateOrder(IOBean instaOrderBean, IOTaskProvider taskProvider) {
        if (instaOrderBean.getInstagram() != null &&
                (instaOrderBean.getStatus() == IOStatus.InProgress || instaOrderBean.getStatus() == IOStatus.Refilling
                        || instaOrderBean.getStatus() == IOStatus.UserChangedToPrivate || instaOrderBean.getStatus() == IOStatus.UserDeleted)) {
            if (instaOrderBean.getUser() != null) {
                Optional<InstagramUserBean> userBean = updateInstagramUser(instaOrderBean, taskProvider);
                boolean isPrivateBefore = instaOrderBean.getUser().isPrivate();
                if (userBean.isPresent()) {
                    if (userBean.get().isPrivate() && !isPrivateBefore) {
                        //Переводим заказ в новый статус, пользователь изменил статус на приватный, нужна обработка в ручном режиме
                        instaOrderBean.setStatus(IOStatus.UserChangedToPrivate);
                        save(instaOrderBean);
                        notificationService.sendMessage("Пользователь " + instaOrderBean.getUser().getUserName() + " перевел аккаунт в private:" +
                                instaOrderBean.getUser().accountUrl() + " Нужно остановить выполнение заказа " + orderUrl(instaOrderBean));
                    } else if (!userBean.get().isPrivate() && isPrivateBefore) {
                        //Был приватным, а сейчас сделал обычный
                        //Значит нужно начать заказ
                        notificationService.sendMessage("Пользователь " + instaOrderBean.getUser().getUserName() + " перевел аккаунт в public:" +
                                instaOrderBean.getUser().accountUrl() + " Нужно добавить в заказ задания " + orderUrl(instaOrderBean));
                    } else if (instaOrderBean.getStatus() == IOStatus.UserDeleted) {
                        instaOrderBean.setStatus(IOStatus.InProgress);
                        save(instaOrderBean);
                        notificationService.sendMessage("Пользователь " + instaOrderBean.getUser().getUserName() + " вернул свой инстаграм:" +
                                instaOrderBean.getUser().accountUrl() + " Можно продолжать выполнение заказа " + orderUrl(instaOrderBean));
                    }
                }/* Так как аккаунты не могут сейчас нормально считываться, то не считаем аккаунты удаленные
                else if (instaOrderBean.getStatus() != IOStatus.UserDeleted) {
                    instaOrderBean.setStatus(IOStatus.UserDeleted);
                    save(instaOrderBean);
                    notificationService.sendMessage("Пользователь " + instaOrderBean.getUser().getUserName() + " удалил свой инстаграм:" +
                            instaOrderBean.getUser().accountUrl() + " Нужно остановить выполнение заказа " +
                            orderUrl(instaOrderBean));
                }*/
            }

            //Обновляем задачи
            ioTaskService.updateTasks(instaOrderBean, taskProvider);
        }
    }

    private String orderUrl(IOBean bean) {
        return APP_ROOT_URL + "instaOrderList/" + bean.getId();
    }

    @Override
    public IOBean save(IOBean bean) {
        //Если изменяется статус, то фиксируем это время
        if (bean.getId() != null) {
            IOBean oldBean = get(bean.getId());
            if (oldBean.getStatus() != bean.getStatus()) {
                bean.setStatusChangeTime(new Date());
                //Также нужно зафиксировать в это время текущую информацию о пользователе в истории, чтобы знать сколько у него было
                //на тот момент подписчиков
                if (bean.getUser() != null) {
                    userService.asyncUpdateUserWithLastMedia(bean.getUser().getUserName());
                } else {
                    log.warn("In order:" + bean + " user is null");
                }
                if (bean.getOrder() != null && (bean.getStatus() == IOStatus.Completed)) {
                    updateOrderCost(bean.getOrder());
                    orderService.asyncCompleteAndSendReceipt(bean.getOrder());
                }
            }
            if (bean.getInstagram() != null && !bean.getInstagram().equals(oldBean.getInstagram())) {
                //При измении инстаграм нужно его поменять в заявке и в контакте
                LeadBean lead = bean.getOrder().getLead();
                if (!bean.getInstagram().equals(lead.getInstagram())) {
                    lead.setInstagram(bean.getInstagram());
                    leadService.save(lead);
                }
                contactDataService.saveContactData(lead.getContact(), ContactDataType.instagram.data(bean.getInstagram()));
            }
        } else {
            //При добавлении проставляем дату создания
            bean.setCreateTime(new Date());
            bean.setIoType(calcType(bean));
        }
        //Так как сейчас не работает получение инстаграм пользователя, то отключаем
        //updateInstagramUser(bean);

        return super.save(bean);
    }

    private void updateInstagramUser(IOBean bean) {
        if (StringHelper.isNotEmpty(bean.getInstagram()) &&
                (bean.getUser() == null || !bean.getUser().getUserName().equals(bean.getInstagram()))) {
            try {
                Optional<InstagramUserBean> user = userService.getOrCreateUser(bean.getInstagram());
                user.ifPresent(bean::setUser);
            } catch (Exception e) {
                log.error("Error on read user: " + bean, e);
            }
        }
    }

    private IOType calcType(IOBean ioBean) {
        if (ioBean.getFollowersPackage() == IOFollowersPackage.vip) {
            return IOType.vip;
        }
        IOType result = null;
        int comboCounter = 0;
        if (ioBean.getFollowersCount() > 0) {
            comboCounter++;
            result = IOType.followers;
        }
        if (ioBean.getLikesCount() > 0) {
            comboCounter++;
            result = IOType.likes;
        }
        if (ioBean.getCommentsCount() > 0) {
            comboCounter++;
            result = IOType.comments;
        }
        if (ioBean.getViewsCount() > 0) {
            comboCounter++;
            result = IOType.views;
        }
        if (comboCounter > 1) {
            return IOType.combined;
        }
        if (result == null) {
            throw new IllegalStateException("Can't count MyIoType for order:" + ioBean);
        }
        return result;
    }

    @Override
    public void delete(IOBean bean) {
        if (bean.getStatus() == IOStatus.InProgress) {
            throw new ClientErrorException("Нельзя удалить заказ в статусе В прогрессе, нужно вначале перевести в другой статус");
        }
        super.delete(bean);
    }

    /**
     * Выполняем автоматически все созданные задания
     */
    public void runAllOrders() {
        execWithNotCompleted(ioTaskService::runTasks);
    }

    public void updateAllTasksProgress() {
        execWithNotCompleted(this::updateOrder);
    }



    private void execWithNotCompleted(BiConsumer<IOBean, IOTaskProvider> consumer) {
        List<IOBean> notCompleted = getNotCompleted();
        MemoryIOTaskProvider memoryIOTaskProvider = ioTaskService.memoryTaskProvider(notCompleted);
        notCompleted.forEach(orderBean -> {
            try {
                consumer.accept(orderBean, memoryIOTaskProvider);
            } catch (Exception e) {
                log.error("Error on exec " + consumer + " with order:" + orderBean, e);
            }
        });
    }

    /**
     * Создаем автоматически задания
     */
    public void addAutoTasks() {
        execWithNotCompleted((instaOrderBean, taskProvider) -> {
            if (instaOrderBean.getStatus() == IOStatus.InProgress || instaOrderBean.getStatus() == IOStatus.Refilling) {
                ioTaskService.addAutoTasks(instaOrderBean, taskProvider);
            }
        });
    }

    /**
     * Изменяем у задач статус
     */
    void changeIoStatuses() {
        for (IOBean instaOrderBean : getAll()) {
            try {
                changeIoStatus(instaOrderBean);
            } catch (Exception e) {
                log.error("Error on change Io status:" + instaOrderBean, e);
            }
        }
    }

    private void changeIoStatus(IOBean instaOrderBean) {
        if (instaOrderBean.getStatus() == IOStatus.InProgress) {
            if (instaOrderBean.getFollowersPackage() == IOFollowersPackage.none) {
                //Если заказ не о подписчиках, а о лайках или комментариях
                MemoryIOTaskProvider taskProvider = ioTaskService.memoryTaskProvider(instaOrderBean);
                if (!needMoreLikesOrComments(instaOrderBean, taskProvider)
                        && taskProvider.getTasks(instaOrderBean, InstaOrderTaskStatus.InProgress).isEmpty()) {
                    instaOrderBean.setStatus(IOStatus.Completed);
                    save(instaOrderBean);
                }
            } else if (instaOrderBean.progressHours().currentPercent() > 100) {
                instaOrderBean.setStatus(IOStatus.Refilling);
                instaOrderBean.setRestoreCreateTime(new Date());
                save(instaOrderBean);
            }
        } else if (instaOrderBean.getStatus() == IOStatus.Confirmed ||
                instaOrderBean.getStatus() == IOStatus.Payed) { //Автоматически начинаем заказ, если инстаграм подтвержден
            startOrder(instaOrderBean);
        } else if (instaOrderBean.getStatus() == IOStatus.Refilling) {
            if (instaOrderBean.refillingHours().currentPercent() >= 100 || instaOrderBean.getFollowersPackage().noRefill()) {
                //И если нет задач в inProgress и подписчиков мы выдали необходимое количество и лайков выдали нужное количество
                MemoryIOTaskProvider taskProvider = ioTaskService.memoryTaskProvider(instaOrderBean);
                IOStats instaOrderStats = new IOStats(instaOrderBean, taskProvider.getTasks(instaOrderBean));
                if (taskProvider.getTasks(instaOrderBean, InstaOrderTaskStatus.InProgress).isEmpty()
                        && instaOrderStats.getFollowers().currentPercent() >= 100
                        && (instaOrderBean.getLikesCount() == 0 || instaOrderStats.getLikes().currentPercent() >= 100)) {
                    instaOrderBean.setStatus(IOStatus.Completed);
                    save(instaOrderBean);
                }
            }
        }
    }

    /**
     * Пересчитать стоимость заказа
     */
    private void updateOrderCost(OrderBean orderBean) {
        List<IOBean> byOrder = getByOrder(orderBean);
        updateOrderCost(orderBean, byOrder, ioTaskService.memoryTaskProvider(byOrder));
    }

    private void updateOrderCost(OrderBean orderBean, List<IOBean> ioBeans, IOTaskProvider taskProvider) {
        if (orderBean.getStatus().paid()) {
            double orderCost = 0;
            for (IOBean ioBean : ioBeans) {
                double cost = getCost(ioBean, taskProvider);
                //Пересчитываем себестоимость продукта
                if (cost > 0) {
                    OrderProductBean orderProduct = ioBean.getOrderProduct();
                    OrderLineBean orderLine = ioBean.getOrderLine();
                    if (orderProduct != null && (orderProduct.getCost() == null || cost != orderProduct.getCost())) {
                        orderProduct.setCost(cost);
                        if (orderLine != null) {
                            orderProduct.setPrice(orderLine.getPrice());
                        }
                        orderProductService.save(orderProduct);
                    }
                    if (orderProduct != null && orderLine != null
                            && orderLine.getPrice() != null && orderProduct.getPrice() != null && !orderLine.getPrice().equals(orderProduct.getPrice())) {
                        orderProduct.setPrice(orderLine.getPrice());
                        orderProductService.save(orderProduct);
                    }
                    if (orderLine != null && (orderLine.getCost() == null || cost != orderLine.getCost())) {
                        orderLine.setCost(cost);
                        orderLineService.save(orderLine);
                    }

                }
                orderCost += cost;
            }

            if (orderCost > 0 && (orderBean.getCostPrice() == null || orderCost != orderBean.getCostPrice())) {
                orderBean.setCostPrice(orderCost);
                orderService.save(orderBean);
            }
            /* Старый вариант расчета
            else {
                for (OrderProductBean orderProductBean : orderProductService.getProducts(orderBean)) {
                    InstaProduct instaProduct = InstaProduct.getByCode(orderProductBean.getProduct().getCode());
                    Double instaProductCost = instaProduct.getCost(ioStats);
                    if (!instaProductCost.equals(orderProductBean.getCost())) {
                        orderProductBean.setCost(instaProductCost);
                        orderProductService.save(orderProductBean);
                    }
                }
            }  */
        }
    }

    private double getCost(IOBean instaOrderBean, IOTaskProvider taskProvider) {
        IOStats ioStats = new IOStats(instaOrderBean, taskProvider.getTasks(instaOrderBean));
        return ioStats.getCharge();
    }

    public List<IOBean> getByOrders(Collection<OrderBean> value) {
        return repository().findByOrders(value);
    }

    public List<IOBean> getOrCreateOrders(CreateIOBean createIOBean) {
        return getOrCreateOrders(createIOBean, orderLineService.getOrderLines(createIOBean.getOrderBean()));
    }

    public List<IOBean> getOrCreateOrders(CreateIOBean createIOBean, List<OrderLineBean> orderLines) {
        OrderBean order = createIOBean.getOrderBean();
        Optional<InstagramUserBean> user = userService.getOrCreateUser(order.getLead().getInstagram());
        /* Так как сейчас не работает получение инстаграм пользователя, поэтому не вызываем эту логику
        if (user.isEmpty() && !accountProvider.isBlocked()) {
            notificationService.sendMessage("Пользователь указал неверный инстаграм: " + order.getLead().getInstagram()
                    + " Нужно написать пользователю на e-mail:" + order.getLead().getEmail());
        }*/
        List<IOBean> beans = new ArrayList<>();
        for (OrderLineBean orderLine : orderLines) {
            IOBean bean = repository().findByOrderLine(orderLine);
            if (bean == null) {
                bean = new IOBean();
                //Устанавливаем скорость по старому
                bean.setOrderStrategy(createIOBean.getFollowersSpeed()
                );
                bean.setOrderLine(orderLine);
                bean.setRegion(IORegion.russia);
                bean.setAccountType(IOAccountType.ALL);
                bean.setMediaDirection(IOMediaDirection.LAST);

                orderLineParser.parse(bean, orderLine.getLineText());
                if (bean.getFollowersCount() == 0 && bean.getLikesCount() == 0 && bean.getCommentsCount() == 0) {
                    continue;
                }
                bean.setOrderProduct(getOrCreateProduct(bean, order));
                bean.setCreateTime(order.getCreateTime());
                bean.setStatus(IOStatus.Created);
                bean.setInstagram(order.getLead().getInstagram());
                user.ifPresent(bean::setUser);

                bean.setOrder(order);
                bean = save(bean);
                //Если нужно, то сразу добавляем задачи на создание комментариев
                String comments = createIOBean.getComments();
                if (bean.getCommentsCount() > 0 && StringHelper.isNotEmpty(comments)) {
                    new CreateCommentTasks().create(bean, comments);
                }
            }
            beans.add(bean);
        }
        return beans;
    }

    private OrderProductBean getOrCreateProduct(IOBean bean, OrderBean order) {
        //Создаем запись о продукте в заказе
        if (bean.getFollowersCount() > 0) {
            return orderProductService.getOrAddProduct(order,
                    bean.getFollowersPackage().name() + InstaProduct.followers.getCode(),
                    bean.getFollowersCount());
        }
        if (bean.getLikesCount() > 0) {
            return orderProductService.getOrAddProduct(order, InstaProduct.likes.getCode(),
                    bean.getLikesCount());
        }
        if (bean.getCommentsCount() > 0) {
            return orderProductService.getOrAddProduct(order, InstaProduct.comments.getCode(),
                    bean.getCommentsCount());
        }
        if (bean.getViewsCount() > 0) {
            return orderProductService.getOrAddProduct(order, InstaProduct.views.getCode(),
                    bean.getViewsCount());
        }
        return null;
    }

    /**
     * Читаем последних 1000 подписчиков, если заказ Premium
     */
    public void readUserLastFollowers(LeadBean leadBean) {
        OrderBean orderBean = orderService.repository().findByLead(leadBean);
        if (isPremium(orderBean)) {
            List<OrderBean> byContact = orderService.repository().findByContact(orderBean.getLead().getContact());
            if (byContact.size() == 1 && byContact.get(0).equals(orderBean)) {
                //Если это первый заказ и он премиум, тогда считываем его подписчиков, чтобы потом можно было что-то предьявить
                notificationService.sendMessage("Считываем 1000 подписчиков у " +
                        InstagramClient.accountURL(orderBean.getLead().getInstagram()));
                userService.readLastUserFollowers(orderBean.getLead().getInstagram(), 1000);
            }
        }
    }

    private boolean isPremium(OrderBean orderBean) {
        List<IOBean> byOrder = getByOrder(orderBean);
        for (IOBean ioBean : byOrder) {
            if (ioBean.getFollowersPackage().getIndex() > IOFollowersPackage.economy.getIndex()) {
                return true;
            }
        }
        return false;
    }

}
