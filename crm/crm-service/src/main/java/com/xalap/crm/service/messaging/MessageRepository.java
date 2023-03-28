/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.messaging;

import com.xalap.crm.service.messaging.thread.MessageThreadBean;
import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 28/05/2019
 */
@Repository
public interface MessageRepository extends PageableRepository<MessageBean, Integer> {

    @EntityGraph(attributePaths = {"thread"})
    List<MessageBean> findAll();

    @EntityGraph(attributePaths = {"thread"})
    List<MessageBean> findByThread(MessageThreadBean thread);

    @EntityGraph(attributePaths = {"thread"})
    List<MessageBean> findByChannelAndThread(MessageChannel channel, MessageThreadBean thread);

    @EntityGraph(attributePaths = {"thread"})
    List<MessageBean> findByMessageType(MessageType messageType, Pageable pageable);

    @EntityGraph(attributePaths = {"thread"})
    List<MessageBean> findByMessageType(MessageType messageType);

    long countByMessageType(MessageType messageType);

}
