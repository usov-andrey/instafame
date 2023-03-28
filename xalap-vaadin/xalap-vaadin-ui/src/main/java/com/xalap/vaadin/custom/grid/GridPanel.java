/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyDefinition;
import com.xalap.framework.utils.ReflectHelper;
import com.xalap.vaadin.custom.component.layout.FlexHorizontalLayout;
import com.xalap.vaadin.custom.crud.binderfieldscreator.BinderFieldsCreator;
import com.xalap.vaadin.custom.crud.binderfieldscreator.BinderHorizontalFieldsCreator;
import com.xalap.vaadin.custom.frame.Navigation;
import com.xalap.vaadin.custom.grid.column.GridColumnBuilder;
import com.xalap.vaadin.starter.ui.layout.size.Horizontal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.xalap.vaadin.custom.frame.ResizableFrame.MOBILE_BREAKPOINT;

/**
 * Панель с гридом
 *
 * @author Усов Андрей
 * @since 16/06/2019
 */
public class GridPanel<B extends Serializable> extends VerticalLayout implements Navigation {

    private final Class<B> beanClass;
    private final Grid<B> grid;
    private final GridDataSource<B> dataSource;
    private final GridFilters<B> filters;
    private final FlexHorizontalLayout actions = new FlexHorizontalLayout();
    private final GridButtons buttons = new GridButtons(this, actions);
    private final GridColumnBuilder<B> columnBuilder;
    private GridColumns<B> columns;

    public GridPanel(Class<B> beanClass) {
        this.beanClass = beanClass;
        grid = new Grid<>(beanClass, false);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        dataSource = new GridDataSource<>(this);
        setColumnsBuilder(new GridColumns<>(grid, beanClass));
        columnBuilder = new GridColumnBuilder<>(this);
        filters = new GridFilters<>(dataSource, actions);
        draw();
    }

    public static <B extends Serializable> Function<Class<?>, GridPanel<B>> createDefault(Class<?> parentClass) {
        return aClass -> {
            Class<B> beanClass = ReflectHelper.getGenericParameterClass(aClass, parentClass, 0);
            return new GridPanel<>(beanClass);
        };
    }

    public GridPanel<B> setColumnsBuilder(GridColumns<B> columns) {
        this.columns = columns;
        columns.setDataSource(dataSource);
        return this;
    }

    public Collection<B> getItems() {
        return dataSource.getItems();
    }

    private void draw() {
        add(actions);
        add(grid);
        setFlexGrow(0, actions);
        setFlexGrow(1, grid);

        setSizeFull();
        expand(grid);
        setSpacing(false);
        setPadding(false);
    }

    public GridButtons buttons() {
        return buttons;
    }

    public Grid<B> grid() {
        return grid;
    }

    @Deprecated
    /*
     * @deprecated use instead #columnsBuilder()
     */
    public GridColumns<B> columns() {
        return columns;
    }

    public GridColumnBuilder<B> columnBuilder() {
        return columnBuilder;
    }

    public Class<B> getBeanClass() {
        return beanClass;
    }

    public GridDataSource<B> dataSource() {
        return dataSource;
    }

    /**
     * Перегрузить данные и перерисовать
     */
    public void refreshAll() {
        dataSource.refreshAll();
    }

    /**
     * Перерисовать не перегружая данные
     */
    public void reDraw() {
        columnBuilder.reDrawActions();
        dataSource.reDraw();
    }

    /**
     * Создаем колонки по-умолчанию
     */
    public void createColumns() {
        grid.getPropertySet().getProperties()
                .filter(property -> !property.isSubProperty())
                .sorted(Comparator.comparing(PropertyDefinition::getName))
                .forEach(bPropertyDefinition -> grid.addColumn(bPropertyDefinition.getName()));
    }

    /*
     * @deprecated Нужно использовать методы addFilter
     */
    @Deprecated
    public GridFilters<B> filters() {
        return filters;
    }

    /**
     * Устнавливаем обработчик по клику на строку грида
     */
    public void setSelectionListener(Consumer<B> listener) {
        grid().setSelectionMode(Grid.SelectionMode.SINGLE);
        grid().addSelectionListener(event -> event.getFirstSelectedItem()
                .ifPresent(listener));
    }

    @Override
    public String toString() {
        return "GridPanel{" +
                "beanClass=" + beanClass +
                ", grid=" + grid +
                '}';
    }


    public <V> void addFilter(V gridFilter) {
        addFilter(gridFilter, BinderFieldsCreator::addFields);
    }

    @SuppressWarnings("unchecked")
    public <V> void addFilter(V gridFilter, Consumer<BinderFieldsCreator<V>> addFieldsConsumer) {
        Binder<V> binder = new Binder<>();
        binder.setBean(gridFilter);
        binder.addValueChangeListener(event -> reDraw());
        BinderHorizontalFieldsCreator<V> binderFieldsCreator = new BinderHorizontalFieldsCreator<>((Class<V>) gridFilter.getClass());
        binderFieldsCreator.setLayout(buttons().getActions());
        binderFieldsCreator.setBinder(binder);
        addFieldsConsumer.accept(binderFieldsCreator);
    }

    public void updateGridForWidth(int width) {
        if (width < MOBILE_BREAKPOINT) {
            actions.setPadding(Horizontal.S);
        }
    }
}
