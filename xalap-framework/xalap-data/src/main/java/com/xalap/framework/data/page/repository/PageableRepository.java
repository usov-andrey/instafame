/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data.page.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Репозиторий с дополнительным методом find
 * 
 * @author Usov Andrey
 * @since 2020-04-30
 */
@NoRepositoryBean
public interface PageableRepository<T, Id> extends JpaRepository<T, Id> {

    List<T> find(Pageable pageable);

}
