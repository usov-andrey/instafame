/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.order;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.product.ProductBean;
import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 16/01/2019
 */
@Repository
public interface OrderRepository extends PageableRepository<OrderBean, Integer> {

    String QUERY = "select o from OrderBean o left join fetch o.lead l left join fetch l.contact c left join fetch l.stage s left join fetch s.pipeline p";
    //Убираем fetch
    String COUNT_QUERY = "select count(o) from OrderBean o left join o.lead l left join l.contact c left join l.stage s left join s.pipeline p";

    String PROFIT_QUERY = "select sum(o.revenue - coalesce(o.costPrice, 0)) from OrderBean o left join o.lead l left join l.contact c left join l.stage s left join s.pipeline p";
    String FILTER = " where (true = :b1 or (l.instagram like %:search% or o.orderId like %:search% or l.name like %:search% or c.name like %:search% or l.utmSource like %:search%))" +
            " and (true = :b2 or o.createTime > :from)" +
            " and (true = :b3 or o.createTime < :to)" +
            " and (true = :b4 or o.status in :statuses)" +
            " and (true = :b5 or o.id in (select payment from OrderProductBean op where op.product = :product))";

    @Override
    @Query(QUERY)
    List<OrderBean> find(Pageable pageable);

    OrderBean findByOrderId(String orderId);

    OrderBean findByLead(LeadBean lead);

    @Query(QUERY +
            " where l.contact = :contact")
    List<OrderBean> findByContact(ContactBean contact);

    @Query(QUERY +
            " where c in :contacts")
    List<OrderBean> findByContacts(Collection<ContactBean> contacts);

    @Query(QUERY)
    @Override
    @NonNull
    List<OrderBean> findAll();

    @Query(QUERY + " where o.createTime > :from and o.status in :statuses")
    List<OrderBean> findFromAndInStatuses(Date from, List<OrderStatus> statuses);

    @Query(QUERY + FILTER)
    List<OrderBean> find(Pageable pageable, boolean b1, String search, boolean b2, Date from, boolean b3, Date to,
                         boolean b4, List<OrderStatus> statuses, boolean b5, ProductBean product);

    @Query(COUNT_QUERY + FILTER)
    long count(boolean b1, String search, boolean b2, Date from, boolean b3, Date to,
               boolean b4, List<OrderStatus> statuses, boolean b5, ProductBean product);

    @Query(PROFIT_QUERY + FILTER)
    Double getProfit(boolean b1, String search, boolean b2, Date from, boolean b3, Date to,
                     boolean b4, List<OrderStatus> statuses, boolean b5, ProductBean product);
}
