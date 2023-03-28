/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.column;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.function.ValueProvider;
import com.xalap.framework.utils.StringHelper;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.GridPanel;
import com.xalap.vaadin.custom.grid.renderer.core.RendererBuilder;
import com.xalap.vaadin.custom.grid.renderer.core.RendererBuilders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.xalap.vaadin.custom.grid.renderer.VerticalRendererBuilder.vertical;

/**
 * @author Usov Andrey
 * @since 2020-05-06
 */
public class GridColumnBuilder<T extends Serializable> implements Serializable {

    private final RendererBuilders<T> rendererBuilders;
    private final GridColumns<T> columns;
    private final Grid<T> grid;
    private final List<SerializableSupplier<RendererBuilder<T>>> actions = new ArrayList<>();

    public GridColumnBuilder(GridPanel<T> gridPanel) {
        this.rendererBuilders = RendererBuilders.create();
        this.columns = gridPanel.columns();
        this.grid = gridPanel.grid();
    }

    public Grid.Column<T> add(String columnName) {
        return columns.addColumn(columnName);
    }

    public void addByNames(String... columnNames) {
        for (String columnName : columnNames) {
            add(columnName);
        }
    }

    @SafeVarargs
    public final Grid.Column<T> add(String header, RendererBuilder<T>... builderArray) {
        RendererBuilders<T> rendererBuildersNew = rendererBuilders.createNew();
        for (RendererBuilder<T> tRendererBuilder : builderArray) {
            rendererBuildersNew.add(tRendererBuilder);
        }
        return setup(header, grid.addColumn(rendererBuildersNew.build()));
    }

    public Grid.Column<T> add(String header, ValueProvider<T, String> valueProvider) {
        return setup(header, grid.addColumn(valueProvider));
    }

    /**
     * Использовать этот метод только при крайней необходимости для скорости разработки
     * Отправка информации о большом количестве компонентов занимает много траффика
     */
    public Grid.Column<T> addComponent(String header, ValueProvider<T, Component> componentProvider) {
        return setup(header, grid.addComponentColumn(componentProvider));
    }

    public Grid.Column<T> addBoolean(String header, ValueProvider<T, Boolean> valueProvider) {
        return add(header, b -> Boolean.TRUE.equals(valueProvider.apply(b)) ? "да" : "нет");
    }

    private Grid.Column<T> setup(String header, Grid.Column<T> column) {
        if (StringHelper.isNotEmpty(header)) {
            column.setHeader(header);
        }
        return column.setAutoWidth(true).setResizable(true);
    }

    @SafeVarargs
    public final void actionsInsert(SerializableSupplier<RendererBuilder<T>>... suppliers) {
        List<SerializableSupplier<RendererBuilder<T>>> supplierList = Arrays.asList(suppliers);
        Collections.reverse(supplierList);
        supplierList.forEach(rendererBuilderSupplier -> actions.add(0, rendererBuilderSupplier));
    }

    @SafeVarargs
    public final void actions(SerializableSupplier<RendererBuilder<T>>... suppliers) {
        actions.addAll(Arrays.asList(suppliers));
    }

    public void reDrawActions() {
        if (!actions.isEmpty()) {
            List<RendererBuilder<T>> builderList = actions.stream().map(Supplier::get).collect(Collectors.toList());
            add("Действия",
                    vertical(builderList));
            actions.clear();
        }

    }

}
