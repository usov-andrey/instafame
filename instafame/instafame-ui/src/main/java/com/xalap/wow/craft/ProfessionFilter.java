package com.xalap.wow.craft;

import com.vaadin.flow.component.combobox.ComboBox;
import com.xalap.framework.utils.Holder;
import com.xalap.vaadin.custom.grid.ComboFilterValue;
import com.xalap.vaadin.custom.grid.GridPanel;
import com.xalap.wow.professionspell.SpellBean;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 15/08/2019
 */
public class ProfessionFilter<B extends Serializable> {

    private final ComboBox<ComboFilterValue<B>> skill;
    private final Holder<Profession> professionHolder = new Holder<>();


    public ProfessionFilter(GridPanel<B> gridPanel) {
        skill = gridPanel.filters().addComboWithoutValues("Skill");
    }

    public void setFilterValue(Profession profession, Collection<SpellBean> receipts, Function<B, SpellBean> receiptBeanFunction) {
        if (professionHolder.get() == null || !profession.equals(professionHolder.get())) {
            professionHolder.set(profession);
            Set<String> skillSet = receipts.stream().map(SpellBean::getSkill).collect(Collectors.toSet());
            List<String> skillList = new ArrayList<String>(skillSet);
            Collections.sort(skillList);
            List<ComboFilterValue<B>> comboFilterValues = new ArrayList<>();
            comboFilterValues.add(new ComboFilterValue<>("Все", b -> true));
            ComboFilterValue<B> defaultValue = null;
            for (String s : skillList) {
                ComboFilterValue<B> filterValue =
                        new ComboFilterValue<>(s, new Predicate<B>() {
                            @Override
                            public boolean test(B b) {
                                return receiptBeanFunction.apply(b).getSkill().equals(s);
                            }
                        });
                comboFilterValues.add(filterValue);
                if (s.startsWith("Kul Tiran")) {
                    defaultValue = filterValue;
                }
            }
            skill.setItems(comboFilterValues);
            skill.setValue(defaultValue);
        }
    }
}
