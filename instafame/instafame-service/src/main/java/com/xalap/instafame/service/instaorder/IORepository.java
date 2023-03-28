/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.orderline.OrderLineBean;
import com.xalap.framework.data.page.repository.PageableRepository;
import com.xalap.instagram.service.user.InstagramUserBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 06/01/2019
 */
@Repository
public interface IORepository extends PageableRepository<IOBean, Integer> {

    String QUERY = "select io from IOBean io " +
            "left join fetch io.user u " +
            "left join fetch io.order o " +
            "left join fetch o.lead l " +
            "left join fetch l.stage s " +
            "left join fetch s.pipeline p " +
            "left join fetch l.contact c " +
            "left join fetch io.orderLine ol " +
            "left join fetch io.orderProduct op";

    String COUNT_QUERY = "select count(io) from IOBean io " +
            "left join io.user u " +
            "left join io.order o " +
            "left join o.lead l " +
            "left join l.stage s " +
            "left join s.pipeline p " +
            "left join l.contact c " +
            "left join io.orderLine ol " +
            "left join io.orderProduct op";

    @Query(QUERY)
    @Override
    List<IOBean> findAll();

    /**
     * По заказу находим Инста заказ
     */
    List<IOBean> findByOrder(OrderBean orderBean);

    List<IOBean> findByUser(InstagramUserBean user);

    boolean existsByUserAndStatusNot(InstagramUserBean user, IOStatus status);

    @Query(QUERY + " where io.status <> :status")
    List<IOBean> findByStatusNot(@Param("status") IOStatus status);

    @Query(QUERY + " where io.order in :orders")
    List<IOBean> findByOrders(@Param("orders") Collection<OrderBean> value);

    @Query(QUERY + " where io.status = :status")
    List<IOBean> findByStatus(@Param("status") IOStatus status);

    IOBean findByOrderLine(OrderLineBean orderLine);

    @Query(QUERY + " where l.contact != :contact and io.status in :statuses and (true = :true1 or io.ioType = :type)")
    List<IOBean> findByContact(Pageable pageable, ContactBean contact, Collection<IOStatus> statuses, boolean true1, IOType type);

    @Query(COUNT_QUERY + " where l.contact != :contact and io.status in :statuses and (true = :true1 or io.ioType = :type)")
    long countByContact(ContactBean contact, Collection<IOStatus> statuses, boolean true1, IOType type);

}
