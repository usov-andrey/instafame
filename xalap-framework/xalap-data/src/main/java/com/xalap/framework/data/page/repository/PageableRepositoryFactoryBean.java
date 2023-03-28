/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data.page.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.lang.NonNull;

import javax.persistence.EntityManager;

/**
 * Класс служит для того, чтобы добавить в стандратную реализацию репозитория новый метод:
 * List<T> find(Pageable pageable) возвращающий List, а не Page
 * @author Usov Andrey
 * @since 2020-04-30
 */
public class PageableRepositoryFactoryBean<R extends JpaRepository<T, Id>, T, Id>
        extends JpaRepositoryFactoryBean<R, T, Id> {

    public PageableRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected @NonNull
    RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new PageableRepositoryFactory(entityManager);
    }

    private static class PageableRepositoryFactory<T, Id> extends JpaRepositoryFactory {

        public PageableRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }

        @Override
        protected @NonNull
        JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information,
                                                              @NonNull EntityManager entityManager) {
            JpaEntityInformation<T, Id> entityInformation = getEntityInformation((Class<T>) information.getDomainType());
            return new PageableRepositoryImpl<>(entityInformation, entityManager);
        }

        @Override
        protected @NonNull
        Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return PageableRepositoryImpl.class;
        }
    }
}
