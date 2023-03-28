/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data.page.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Добавляем метод find
 * @author Usov Andrey
 * @since 2020-04-30
 */
public class PageableRepositoryImpl<T, Id> extends SimpleJpaRepository<T, Id> implements PageableRepository<T, Id> {

    public PageableRepositoryImpl(JpaEntityInformation<T, Id> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public List<T> find(Pageable pageable) {
        TypedQuery<T> query = getQuery(null, pageable);
        return readPage(query, getDomainClass(), pageable, null).getContent();
    }

}
