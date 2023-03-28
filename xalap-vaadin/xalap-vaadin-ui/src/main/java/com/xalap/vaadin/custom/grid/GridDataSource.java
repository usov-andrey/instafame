/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid;

import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.SerializableSupplier;
import com.xalap.framework.domain.filter.Filter;
import com.xalap.vaadin.custom.data.DataChangeListeners;
import com.xalap.vaadin.custom.grid.dataprovider.GridSqlDataProvider;
import com.xalap.vaadin.custom.grid.dataprovider.filter.GridFilterSqlDataProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Источник данных для грида
 * Образается к GridDataProvider за данными
 * @author Усов Андрей
 * @since 08/06/2019
 */
public class GridDataSource<B extends Serializable> extends DataChangeListeners<Collection<B>> implements Serializable {

    private final GridPanel<B> gridPanel;
    private final List<SerializablePredicate<B>> filters = new ArrayList<>();
    private DataProvider<B, ?> dataHolder;
    private SerializableComparator<B> sortComparator;
    private SerializableSupplier<Collection<B>> dataProvider;

    private DataProvider<B, ?> filterablePageableDataProvider;

    public GridDataSource(GridPanel<B> gridPanel) {
        this.gridPanel = gridPanel;
    }

    public void setMemoryDataProvider(SerializableSupplier<Collection<B>> dataProvider) {
        this.dataProvider = dataProvider;
    }

    /**
     * Задаем постраничное получение данных
     */
    public void setPageableDataProvider(GridSqlDataProvider<B> dataProvider) {
        this.filterablePageableDataProvider = dataProvider;
    }

    /**
     * Задаем постраничное получение данных + фильтр
     */
    public <F extends Filter> void setPageableDataProvider(GridFilterSqlDataProvider<B, F> dataProvider, F filter) {
        this.filterablePageableDataProvider = dataProvider;
        dataProvider.setFilter(filter);
    }

    public void setSortComparator(SerializableComparator<B> sortComparator) {
        this.sortComparator = sortComparator;
    }

    /**
     * Загружает заново данные и переписовывает
     */
    public void refreshAll() {
        if (dataProvider == null && filterablePageableDataProvider == null) {
            throw new IllegalStateException("Not found dataProvider in grid:" + gridPanel);
        }
        if (filterablePageableDataProvider == null) {
            //Получаем, фильтруем, сортируем
            Collection<B> values = dataProvider.get();

            Stream<B> stream = values.stream();

            if (!filters.isEmpty()) {
                stream = getStream(stream);
            }

            if (sortComparator != null) {
                stream = stream.sorted(sortComparator);
            }

            dataHolder = DataProvider.fromStream(stream);

        } else {
            dataHolder = filterablePageableDataProvider;
        }
        gridPanel.grid().setDataProvider(dataHolder);
        dataChanged();
    }

    private Stream<B> getStream(Stream<B> stream) {
        return stream.filter(b -> {
            for (Predicate<B> filter : filters) {
                if (!filter.test(b)) {
                    return false;
                }
            }
            return true;
        });
    }

    private void dataChanged() {
        onDataChange(getItems());
        dataHolder.refreshAll();
    }

    /**
     * Просто происходит перерисовка, данные не изменились
     */
    public void reDraw() {
        if (dataHolder != null) {
            dataHolder.refreshAll();
        } else {
            refreshAll();
        }
    }

    public void addFilter(SerializablePredicate<B> filter) {
        filters.add(filter);
    }


    public void filterChanged() {
        refreshAll();
    }

    public Collection<B> getItems() {
        if (filterablePageableDataProvider == null) {
            return ((ListDataProvider<B>) dataHolder).getItems();
        } else {
            return new ArrayList<>();
        }
    }

}
