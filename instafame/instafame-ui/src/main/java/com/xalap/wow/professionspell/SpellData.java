package com.xalap.wow.professionspell;

import com.xalap.wow.item.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 24/11/2019
 */
public class SpellData {

    private ItemBean item;
    private List<ItemBean> recipes = new ArrayList<>();

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public List<ItemBean> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<ItemBean> recipes) {
        this.recipes = recipes;
    }
}
