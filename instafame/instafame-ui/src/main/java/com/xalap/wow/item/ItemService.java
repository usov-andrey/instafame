package com.xalap.wow.item;

import com.xalap.framework.data.CrudService;
import com.xalap.framework.logging.HasLog;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.wow.api.WowApi;
import com.xalap.wow.api.classic.item.Binding;
import com.xalap.wow.api.classic.item.ClassicItem;
import com.xalap.wow.api.item.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Predicate;

/**
 * @author Усов Андрей
 * @since 28.06.2018
 */
@Service
public class ItemService extends CrudService<ItemBean, ItemRepository, Integer> implements HasLog {

    @Autowired
    private WowApi api;

    /**
     * @return Все вещи из базы
     */
    public Map<Long, ItemBean> getAllItems() {
        return CollectionHelper.createIdMap(getRepository().findAll(), ItemBean::getItemId);
    }

    public synchronized ItemBean getItemClassic(long itemId) {
        ItemBean bean = repository().findByItemId(itemId);

        if (bean == null) {
            ClassicItem item = api.getClassicItem(itemId);
            bean = new ItemBean();
            Long id = item.getId();
            bean.setItemId(id);
            bean.setName(item.getName());
            bean.setSellPrice(item.getSellPrice());
            bean.setBuyPrice(item.getPurchasePrice());
            bean.setQuality(ItemQuality.fromString(item.getQuality().getType()));
            Binding binding = item.getPreviewItem().getBinding();
            bean.setBindType(binding != null ? BindType.fromString(binding.getType()) : BindType.none);
            bean.setCanEquip(item.getIsEquippable());
            bean = save(bean);
        }
        return bean;
    }

    /**
     * возвращаем null, если создать такой item не удалось
     */
    public synchronized ItemBean getItem(long itemId) {
        return reloadItem(itemId, bean -> bean == null || bean.getBindType() == null);
    }

    public ItemBean reloadItem(long itemId) {
        return reloadItem(itemId, bean -> true);
    }

    public ItemBean reloadItem(long itemId, Predicate<ItemBean> reloadFunction) {
        ItemBean bean = repository().findByItemId(itemId);
        if (!reloadFunction.test(bean)) {
            return bean;
        }
        ItemResponse item = api.getItem(itemId);
        Integer id = bean != null ? bean.getId() : null;
        bean = new ItemBean();
        bean.setId(id);
        bean.setItemId(itemId);

        if (item == null) {
            bean.setName("NOT_FOUND");
        } else {
            bean.setName(item.getName());
            bean.setSellPrice(item.getSellPrice());
            bean.setBuyPrice(item.getBuyPrice());
            bean.setQuality(ItemQuality.values()[item.getQuality()]);
            bean.setBindType(BindType.values()[item.getItemBind()]);
            bean.setCanEquip(item.getEquippable());
            bean.setCanInAuction(item.getIsAuctionable());
        }

        bean = save(bean);
        //}
        return bean;
    }

}
