/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.nakrutkaby;

import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.thread.ThreadService;
import com.xalap.framework.utils.Holder;
import com.xalap.framework.utils.StringHelper;
import com.xalap.framework.utils.WebHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.SeleniumService;
import com.xalap.instafame.service.cheat.exception.NoBalanceException;
import com.xalap.instafame.service.cheat.provider.CheatTaskProvider;
import com.xalap.instafame.service.instaorder.task.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Усов Андрей
 * @since 12/01/2019
 */
@Service
public class NakrutkaByProvider extends CheatTaskProvider {

    protected static final Logger log = LoggerFactory.getLogger(NakrutkaByProvider.class);

    private final SeleniumService seleniumService;
    private final ThreadService threadService;

    public NakrutkaByProvider(SeleniumService seleniumService, ThreadService threadService) {
        this.seleniumService = seleniumService;
        this.threadService = threadService;
    }

    @Override
    public String getApiKey() {
        return "asdasdasd";
    }

    @Override
    public String getApiUrl() {
        return "http://smm.nakrutka.by/api";
    }

    private String getApiUrlWithKey() {
        return getApiUrl() + "/?key=" + getApiKey();
    }

    private String getApiCreateOrder() {
        return getApiUrlWithKey() + "&action=create&service=%s&quantity=%s&link=%s";
    }

    private String getApiCreateOrderWithSpeed() {
        return getApiCreateOrder() + "&speed=%s";
    }

    private String getApiOrderStatus() {
        return getApiUrlWithKey() + "&action=status&order=%s";
    }

    public String orderComment(String url, String text, int serviceNumber) {
        Holder<String> lastOrderNo = new Holder<>();
        seleniumService.withFireFoxDriver(new Consumer<WebDriver>() {
            @Override
            public void accept(WebDriver webDriver) {
                login(webDriver);
                orderComment(webDriver, url, text, serviceNumber);
                lastOrderNo.set(getLastOrderNo(webDriver, serviceNumber));
            }
        });
        return lastOrderNo.get();
    }

    private void login(WebDriver webDriver) {
        seleniumService.navigate(webDriver, "https://nakrutka.by/login.php", 2000);
        String html = webDriver.getPageSource();
        log.debug("response:" + html);
        String key = StringHelper.getStringBetween(html, "name=\"keys\" value=\"", "\"");
        log.debug("Key:" + key);
        webDriver.findElement(By.name("useremail")).sendKeys("anomys");
        //webDriver.findElement(By.name("keys")).sendKeys(key);
        webDriver.findElement(By.name("password")).sendKeys("dIliNaYS");
        //webDriver.findElement(By.name("action")).sendKeys("login");
        new WebDriverWait(webDriver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.id("login-btn")));
        webDriver.findElement(By.id("login-btn")).click();
        threadService.sleep(5000);
        /*
        seleniumService.navigate(webDriver, "https://nakrutka.by/new-order.php", 1000);
        html = webDriver.getPageSource();
        log.debug("response:" + html);*/
    }

    private void orderComment(WebDriver webDriver, String url, String text, int serviceNumber) {
        seleniumService.navigate(webDriver, "https://nakrutka.by/new-order.php", 1000);
        new WebDriverWait(webDriver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.id("category")));
        String html = webDriver.getPageSource();
        log.debug("response:" + html);
        seleniumService.selectOption(webDriver, By.id("category"), "6");//Комментарии
        new WebDriverWait(webDriver, 10).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//select[@id='service']//option[@value = " + Quotes.escape("" + serviceNumber) + "]")));
        seleniumService.selectOption(webDriver, By.id("service"), "" + serviceNumber);//Собственные комментарии
        new WebDriverWait(webDriver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.name("comments")));
        webDriver.findElement(By.name("link")).sendKeys(url);
        webDriver.findElement(By.name("comments")).sendKeys(text);
        webDriver.findElement(By.name("order")).click();
    }

    private String getLastOrderNo(WebDriver webDriver, int serviceNumber) {
        String cheatTaskName = serviceNumber + ". ";
        //Все заказы
        seleniumService.navigate(webDriver, "https://nakrutka.by/all-orders.php", 3000);
        new WebDriverWait(webDriver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("all-orders")));
        String html = seleniumService.getHtml(webDriver);
        log.debug("response:" + html);

        for (WebElement tr : webDriver.findElement(By.id("all-orders")).findElements(By.tagName("tr"))) {
            log.debug("tr:" + tr.getText());
            List<WebElement> td = tr.findElements(By.tagName("td"));
            if (!td.isEmpty()) {
                String orderNo = td.get(1).getText();
                String taskName = td.get(2).getText();
                if (taskName.startsWith(cheatTaskName)) {
                    return orderNo;
                }
            }
        }
        throw new IllegalStateException("Not found task:" + serviceNumber + " in all-orders html:" + html);
    }


    private String decode(String value) {
        return StringEscapeUtils.unescapeJava(value);
    }


    //http://smm.nakrutka.by/api/?key=f270cf244cbd8490a15e80cdef0fd947&action=create&service=3&quantity=200&link=https://www.instagram.com/jaholper/
    public NakrutkaByCreateOrder send(int quantity, String link, int serviceNumber, Integer speed) throws ServiceTemporaryException {
        return createOrder(speed != null ? String.format(getApiCreateOrderWithSpeed(), serviceNumber, quantity, link, speed) :
                String.format(getApiCreateOrder(), serviceNumber, quantity, link));
    }

    private NakrutkaByCreateOrder createOrder(String url) throws ServiceTemporaryException {
        return execNak(url, NakrutkaByCreateOrder.class);
    }

    public NakrutkaByOrderStatus orderStatus(String orderId) throws ServiceTemporaryException {
        String url = String.format(getApiOrderStatus(), orderId);
        return execNak(url, NakrutkaByOrderStatus.class);
    }

    private <T extends NakrutkaByResponse> T execNak(String url, Class<T> responseClass) throws ServiceTemporaryException {
        String json = execJson(url);
        T response = convert(json, responseClass);
        if (StringHelper.isNotEmpty(response.getError())) {
            String error = WebHelper.decodeURL(response.getError());
            if (error.equals("Недостаточно средств")) {
                throw new NoBalanceException("NakrutkaBy");
            } else if (error.contains("Possibly")) {
                //Иногда сервис выдает такую ошибку, обычно означает, что нужно повторить позже
                throw new ServiceTemporaryException("Error on exec url:" + url + "\nresponse:" + json);
            }
            throw new IllegalStateException("Error on exec url:" + url + " response:" + json + " error:" + error);
        }
        return response;
    }

    private String getJson(String html) {
        //Так работало получение json из html в ChromeDriver
        html = StringHelper.getStringBefore(html, "</pre>");
        return html.substring(html.lastIndexOf(">") + 1);
    }

    @Override
    public String getName() {
        return "NakrutkaBy";
    }

    @Override
    public List<CheatTaskBean> getActualTasks() {
        List<CheatTaskBean> result = new ArrayList<>();
        seleniumService.withFireFoxDriver(new Consumer<WebDriver>() {
            @Override
            public void accept(WebDriver webDriver) {
                result.addAll(getActualTasks(webDriver));
            }
        });
        return result;
    }

    private List<CheatTaskBean> getActualTasks(WebDriver webDriver) {
        List<CheatTaskBean> result = new ArrayList<>();
        //Сервисы
        seleniumService.navigate(webDriver, "https://nakrutka.by/services.php", 3000);
        new WebDriverWait(webDriver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("services")));
        String html = seleniumService.getHtml(webDriver);
        log.debug("response:" + html);

        for (WebElement tr : webDriver.findElement(By.id("services")).findElements(By.tagName("tr"))) {
            log.debug("tr:" + tr.getText());
            List<WebElement> td = tr.findElements(By.tagName("td"));
            if (!td.isEmpty()) {
                Optional<CheatTaskBean> cheatTaskBean = readTask(td);
                cheatTaskBean.ifPresent(result::add);
            }
        }
        return result;
    }

    private Optional<CheatTaskBean> readTask(List<WebElement> td) {
        CheatTaskBean bean = new CheatTaskBean();
        String id = td.get(0).getText();
        bean.setServiceNumber(Integer.parseInt(id));
        String name = td.get(1).getText();
        //Имя вида 12. Просмотры видео - 2р
        //Убираем номер сервиса
        name = name.substring(name.indexOf(".") + 1).trim();
        if (name.startsWith("Подписчики")) {
            bean.setTaskType(InstaOrderTaskType.followers);
        } else if (name.startsWith("Лайки") && !name.startsWith("Лайки на комментарий")) {
            bean.setTaskType(InstaOrderTaskType.likes);
        } else if (name.startsWith("Комментарии")) {
            bean.setTaskType(InstaOrderTaskType.comments);
        } else if (name.startsWith("Просмотры видео")) {
            bean.setTaskType(InstaOrderTaskType.views);
        } else {
            return Optional.empty();
        }
        bean.setName(name);

        String desc = td.get(2).getText();
        bean.setDescription(desc);
        String min = td.get(3).getText().replace(" ", "").replace(" ", "");//Могут быть числа: 5 000
        bean.setMin(Integer.parseInt(min));
        String max = td.get(4).getText().replace(" ", "").replace(" ", "");
        bean.setMax(Integer.parseInt(max));
        String priceFor1000 = td.get(6).getText();
        //Стоимость в виде
        //	125p за 1000
        priceFor1000 = priceFor1000.substring(0, priceFor1000.indexOf("p")).trim();
        bean.setCostFor1000(Double.parseDouble(priceFor1000));

        if (name.contains("⏳")) {
            bean.setSpeedSupport(true);
        }

        bean.setProviderName(getName());
        return Optional.of(bean);
    }

    @Override
    public void abort(IOTaskBean taskBean) {
        //Функция не поддерживается, считаем, что за заказ мы заплатили в полном обьеме, но ничего не получили
        taskBean.setStatus(InstaOrderTaskStatus.Interrupted);
    }

    @Override
    public void update(IOTaskBean bean) throws ServiceTemporaryException {
        NakrutkaByOrderStatus order = orderStatus(bean.getExtOrderId());
        updateOrder(bean, order);

    }

    private void updateOrder(IOTaskBean bean, NakrutkaByOrderStatus order) {
        new NakrutkaByOrderProcessor().updateOrder(bean, order);
    }

    @Override
    public void runFollowers(IOFollowersTaskBean taskBean) throws ServiceTemporaryException {
        CheatTaskBean cheatTask = taskBean.getCheatTask();
        NakrutkaByCreateOrder order = send(taskBean.getQuantity(), taskBean.getOrder().accountUrl(),
                cheatTask.getServiceNumber(),
                cheatTask.isSpeedSupport() ? taskBean.getCountSpeed() : null);
        taskBean.setExtOrderId(order.getOrderId());
    }

    @Override
    public void runLikes(IOLikesTaskBean taskBean) throws ServiceTemporaryException {
        runWithMedia(taskBean);
    }

    @Override
    public void runComments(IOCommentsTaskBean taskBean) throws ServiceTemporaryException {
        CheatTaskBean cheatTask = taskBean.getCheatTask();
        String orderId = orderComment(taskBean.url(), taskBean.getComments(), cheatTask.getServiceNumber());
        taskBean.setExtOrderId(orderId);
    }

    @Override
    public void runViews(IOViewsTaskBean taskBean) throws ServiceTemporaryException {
        runWithMedia(taskBean);
    }

    private void runWithMedia(AbstractMediaTaskBean taskBean) throws ServiceTemporaryException {
        CheatTaskBean cheatTask = taskBean.getCheatTask();
        NakrutkaByCreateOrder order = send(taskBean.getQuantity(), taskBean.url(),
                cheatTask.getServiceNumber(),
                cheatTask.isSpeedSupport() ? taskBean.getCountSpeed() : null);
        taskBean.setExtOrderId(order.getOrderId());
    }

    @Override
    public void refill(IOTaskBean bean) throws ServiceTemporaryException {
        throw new UnsupportedOperationException();
    }
}
