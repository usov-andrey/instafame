package com.xalap.wow.item;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Усов Андрей
 * @since 28.06.2018
 */
@Repository
public interface ItemRepository extends PageableRepository<ItemBean, Integer> {

    ItemBean findByItemId(long itemId);
}
