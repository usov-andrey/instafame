/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.authority;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 *
 * @author Usov Andrey
 * @since 2020-04-06
 */
@Repository
public interface AuthorityRepository extends PageableRepository<Authority, String> {

    Optional<Authority> findOneByName(String name);
}
