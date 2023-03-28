/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.product;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Усов Андрей
 * @since 18/01/2019
 */
@Repository
public interface ProductRepository extends PageableRepository<ProductBean, Integer> {

    ProductBean findByNameAndPrice(String name, Double price);

    ProductBean findByCode(String code);
}
