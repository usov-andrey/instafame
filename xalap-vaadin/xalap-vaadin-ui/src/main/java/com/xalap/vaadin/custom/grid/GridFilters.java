/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.StringHelper;
import com.xalap.vaadin.custom.component.IntegerField;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * @deprecated
 * @author Усов Андрей
 * @since 08/06/2019
 */
@Deprecated
public class GridFilters<B extends Serializable> implements Serializable{

    private final GridDataSource<B> dataProvider;
    private final HasComponents actions;

    public GridFilters(GridDataSource<B> dataProvider, HasComponents actions) {
        this.dataProvider = dataProvider;
        this.actions = actions;
    }

    public GridFilters<B> addLocalDate(String caption, BiPredicate<B, LocalDate> predicate) {
        DatePicker filter = new DatePicker();
        add(caption, filter, predicate);
        return this;
    }

    public GridFilters<B> addCombo(String caption, List<ComboFilterValue<B>> filterValues) {
        return addCombo(caption, filterValues, filterValues.get(0));
    }

    public GridFilters<B> addCombo(String caption, List<ComboFilterValue<B>> filterValues, ComboFilterValue<B> defaultValue) {
        ComboBox<ComboFilterValue<B>> comboBox = createCombo();
        comboBox.setItems(filterValues);
        comboBox.setValue(defaultValue);
        addCombo(comboBox, caption);
        return this;
    }

    public ComboBox<ComboFilterValue<B>> addComboWithoutValues(String caption) {
        ComboBox<ComboFilterValue<B>> comboBox = createCombo();
        add(caption, comboBox, (b, filterValue) -> filterValue.getPredicate().test(b));
        return comboBox;
    }

    private ComboBox<ComboFilterValue<B>> createCombo() {
        ComboBox<ComboFilterValue<B>> comboBox = new ComboBox<>();
        comboBox.setItemLabelGenerator(ComboFilterValue::getValue);
        return comboBox;
    }

    private ComboBox<ComboFilterValue<B>> addCombo(ComboBox<ComboFilterValue<B>> comboBox, String caption) {
        comboBox.setItemLabelGenerator(ComboFilterValue::getValue);
        add(caption, comboBox, (b, filterValue) -> filterValue.getPredicate().test(b));
        return comboBox;
    }

    public GridFilters<B> addText(String caption, BiPredicate<B, String> predicate) {
        TextField filter = new TextField();
        add(caption, filter, predicate);
        return this;
    }

    public GridFilters<B> addInteger(String caption, BiPredicate<B, Integer> predicate) {
        IntegerField filter = new IntegerField();
        filter.setWidth("50px");
        add(caption, filter, predicate);
        return this;
    }

    public GridFilters<B> addDate(String caption, BiPredicate<B, Date> predicate) {
        DatePicker filter = new DatePicker();
        add(caption, filter, (b, localDate) -> predicate.test(b, DateHelper.date(localDate)));
        return this;
    }

    private <C extends AbstractField<C, T>, T> void add(String caption, AbstractField<C, T> filter, BiPredicate<B, T> predicate) {
        filter.addValueChangeListener(event -> {
            dataProvider.filterChanged();
        });
        dataProvider.addFilter(b -> filter.getValue() == null || predicate.test(b, filter.getValue()));
        if (StringHelper.isNotEmpty(caption)) {
            actions.add(new Label(caption));
        }
        actions.add(filter);
    }

}
