/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.frame;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.function.SerializableRunnable;
import com.vaadin.flow.function.SerializableSupplier;
import com.xalap.framework.domain.filter.Filter;
import com.xalap.framework.domain.filter.SearchFilter;
import com.xalap.framework.domain.page.PageableService;
import com.xalap.framework.logging.HasLog;
import com.xalap.vaadin.custom.crud.binderfieldscreator.BinderFieldsCreator;
import com.xalap.vaadin.custom.grid.GridPanel;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.custom.grid.dataprovider.GridSqlDataProvider;
import com.xalap.vaadin.custom.grid.dataprovider.filter.GridFilterSqlDataProvider;
import com.xalap.vaadin.custom.grid.dataprovider.filter.GridSearchSqlDataProvider;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.vaadin.starter.ui.components.FlexBoxLayout;
import com.xalap.vaadin.starter.ui.components.navigation.bar.AppBar;
import com.xalap.vaadin.starter.ui.layout.size.Horizontal;
import com.xalap.vaadin.starter.ui.layout.size.Top;
import com.xalap.vaadin.starter.ui.util.css.BoxSizing;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Отображает просто грид
 *
 * @author Усов Андрей
 * @since 07/06/2019
 */
public abstract class RootListFrame<B extends Serializable> extends ResizableFrame implements Navigation, HasLog {

    private final LinkedHashMap<String, SerializableRunnable> tabs = new LinkedHashMap<>();
    protected GridPanel<B> gridPanel;

    protected RootListFrame(Function<Class<?>, GridPanel<B>> gridPanelSupplier) {
        gridPanel = gridPanelSupplier.apply(getClass());
        setViewContent(createContent(gridPanel));
    }

    protected RootListFrame() {
        this(GridPanel.createDefault(RootListFrame.class));
    }

    /**
     * Нужно использовать только в том случае, когда не предполагается получение большого количества данных из базы
     * Если может вернуться более 1000 строчек, то нужно использовать #setPageableDataProvider
     */
    protected void setMemoryDataProvider(SerializableSupplier<Collection<B>> itemsSupplier) {
        gridPanel.dataSource().setMemoryDataProvider(itemsSupplier);
    }

    protected <F extends Filter> void setPageableDataProvider(GridFilterSqlDataProvider<B, F> dataProvider, F filter) {
        gridPanel.dataSource().setPageableDataProvider(dataProvider, filter);
        gridPanel.addFilter(filter);
    }

    /**
     * Задаем постраничное получение с фильтром
     */
    protected <F extends Filter> void setPageableDataProvider(GridFilterSqlDataProvider<B, F> dataProvider, F filter,
                                                              Consumer<BinderFieldsCreator<F>> addFieldsConsumer) {
        gridPanel.dataSource().setPageableDataProvider(dataProvider, filter);
        gridPanel.addFilter(filter, addFieldsConsumer);
    }

    protected <T extends SearchFilter> BinderFieldsCreator<T> addSearchFilter(BinderFieldsCreator<T> fieldsCreator) {
        TextField searchField = new TextField();
        searchField.setPlaceholder("Найти");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        Binder.BindingBuilder<T, String> bindingBuilder = fieldsCreator.addField(searchField);
        bindingBuilder.bind(SearchFilter::getSearch, SearchFilter::setSearch);
        return fieldsCreator;
    }

    /**
     * Задаем пострачное получение данных c поиском
     */
    protected void setDataProviderWithSearch(GridSearchSqlDataProvider<B> dataProvider) {
        setPageableDataProvider(dataProvider, new SearchFilter());
    }

    /**
     * Задаем пострачное получение данных без фильтра
     */
    protected void setDataProvider(ServiceRef<? extends PageableService<B>> crudService, GridDefaultSorting sort) {
        gridPanel.dataSource().setPageableDataProvider(new GridSqlDataProvider<>(crudService, sort));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        initTabs();
        gridPanel.reDraw();
    }

    /**
     * Добавляем вкладку без какого-то фильтра dataSource
     * @deprecated use instead другой addTab
     */
    @Deprecated
    public RootListFrame<B> addTab(String caption) {
        return addTab(caption, () -> {
        });
    }

    /**
     * @deprecated use instead другой addTab
     */
    @Deprecated
    public RootListFrame<B> addTab(String caption, Predicate<B> predicate) {
        addTab(caption);
        gridPanel.dataSource().addFilter(b -> {
            Tab selectedTab = getSelectedTab();
            return selectedTab == null || !selectedTab.getLabel().equals(caption) || predicate.test(b);
        });
        return this;
    }

    public RootListFrame<B> addTab(String caption, SerializableRunnable runnable) {
        tabs.put(caption, runnable);
        return this;
    }

    /**
     * При загрузке, когда никакая вкладка еще не выбрана или не добавлена метод вернет null
     */
    protected Tab getSelectedTab() {
        return MainLayout.get().getAppBar().getSelectedTab();
    }

    private void initTabs() {
        AppBar appBar = MainLayout.get().getAppBar();

        tabs.keySet().forEach(appBar::addTab);
        appBar.addTabSelectionListener(e -> {
            //В случае, когда мы удаляем вкладки при переходе на другой экран, на этом экране вызывается этот обработчки
            //Такое поведение было исправлено в CustomAppBar
            if (e.getSource().getSelectedTab() != null) {
                Runnable runnable = tabs.get(e.getSource().getSelectedTab().getLabel());
                runnable.run();
                reloadData();
            }
        });
        appBar.centerTabs();
    }

    protected void reloadData() {
        log().debug("reloadData");
        gridPanel.refreshAll();
    }

    /**
     * Выполнить код и после перегрузить данные и грид
     */
    protected Runnable withReloadData(Runnable runnable) {
        return () -> {
            runnable.run();
            reloadData();
        };
    }

    private Component createContent(Component grid) {
        FlexBoxLayout content = new FlexBoxLayout(grid);
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        return content;
    }

    @Override
    protected void updateFrameForWidth(int width) {
        super.updateFrameForWidth(width);
        gridPanel.updateGridForWidth(width);
    }
}
