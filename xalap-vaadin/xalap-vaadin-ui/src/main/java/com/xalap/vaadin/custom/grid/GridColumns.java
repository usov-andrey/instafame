/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.HasUrlParameter;
import com.xalap.framework.domain.annotation.BeanAnnotationInfo;
import com.xalap.framework.domain.annotation.BeanAnnotationInfoProvider;
import com.xalap.framework.domain.annotation.FieldAnnotationInfo;
import com.xalap.framework.domain.holder.IdHolderWithName;
import com.xalap.framework.utils.StringHelper;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;
import com.xalap.vaadin.custom.grid.valueprovider.BigDecimalValueProvider;
import com.xalap.vaadin.custom.grid.valueprovider.DateTimeValueProvider;
import com.xalap.vaadin.custom.grid.valueprovider.DateValueProvider;
import com.xalap.vaadin.custom.grid.valueprovider.InstantValueProvider;
import com.xalap.vaadin.custom.route.RouterLink;
import com.xalap.vaadin.starter.ui.util.LumoStyles;
import com.xalap.vaadin.starter.ui.util.UIUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @deprecated
 * use instead #GridColumnBuilder
 * @author Усов Андрей
 * @since 18/04/2019
 */
@Deprecated
public class GridColumns<B extends Serializable> implements Serializable {

    private final Grid<B> grid;
    private final Class<B> beanClass;
    private final ArrayList<SerializableBiConsumer<GridActions<B>, B>> actionsConsumers = new ArrayList<>();
    private GridDataSource<B> dataSource;

    public GridColumns(Grid<B> grid, Class<B> beanClass) {
        this.grid = grid;
        this.beanClass = beanClass;
        grid.removeAllColumns();
    }

    public void setDataSource(GridDataSource<B> dataSource) {
        this.dataSource = dataSource;
    }

    public GridColumns<B> add(String... columnNames) {
        for (String columnName : columnNames) {
            addColumn(columnName);
        }
        return this;
    }

    public Grid.Column<B> addColumn(Renderer<B> renderer) {
        return grid.addColumn(renderer).setResizable(true);
    }

    public Grid.Column<B> addColumn(String columnName) {
        BeanAnnotationInfo info = BeanAnnotationInfoProvider.getInstance().getInfo(beanClass);
        FieldAnnotationInfo field = info.getField(columnName);
        Grid.Column<B> column = grid.addColumn(field.getName());
        if (field.getCaption() != null) {
            column.setHeader(field.getCaption());
        }

        if (field.isDate() || field.isLocalDate()) {
            column.setWidth("180px");
        } else if (field.isIntOrInteger() || field.isDouble()) {
            column.setWidth("70px");
        } else if (field.isString()) {
            column.setWidth("200px");
        }
        column.setResizable(true);
        return column;
    }

    public Grid.Column<B> addColumn(String header, ValueProvider<B, String> valueProvider) {
        return grid.addColumn(valueProvider).setHeader(header).setResizable(true);
    }

    public Grid.Column<B> addDate(String header, SerializableFunction<B, Date> valueProvider) {
        return addSortColumn(header, new DateValueProvider<B>(valueProvider));
    }

    public Grid.Column<B> addDateTime(String header, SerializableFunction<B, Date> valueProvider) {
        return addSortColumn(header, new DateTimeValueProvider<B>(valueProvider));
    }

    public Grid.Column<B> addInstant(String header, SerializableFunction<B, Instant> valueProvider) {
        return addSortColumn(header, new InstantValueProvider<B>(valueProvider));
    }

    public Grid.Column<B> addDecimal(String header, ValueProvider<B, BigDecimal> valueProvider) {
        return grid.addComponentColumn((ValueProvider<B, Component>) b -> {
            Label label = UIUtils.createH5Label(new BigDecimalValueProvider<>(valueProvider).apply(b));
            label.addClassName(LumoStyles.FontFamily.MONOSPACE);
            return label;
        }).setHeader(header).setSortable(true).setComparator(Comparator.comparing(valueProvider)).setResizable(true);
    }

    private Grid.Column<B> addSortColumn(String header, ValueProvider<B, String> valueProvider) {
        return grid.addColumn(valueProvider).setHeader(header).setSortable(true)
                .setComparator(Comparator.comparing(valueProvider)).setWidth("100px").setResizable(true);
    }

    public Grid.Column<B> addComponent(String header, ValueProvider<B, Component> componentProvider) {
        Grid.Column<B> column = grid.addComponentColumn(componentProvider);
        if (StringHelper.isNotEmpty(header)) {
            column.setHeader(header);
        }
        column.setResizable(true);
        return column;
    }

    public Grid.Column<B> addText(String header, Function<B, String> textProvider, String width, String height) {
        return addComponent(header, bean -> {
            Dialog dialog = new Dialog();
            dialog.add(new Label(textProvider.apply(bean)));
            dialog.setCloseOnOutsideClick(false);

            TextArea text = new TextArea();
            text.setHeight(height);
            text.setWidth(width);
            text.setValue(textProvider.apply(bean));
            text.setReadOnly(true);

            text.addFocusListener(event -> dialog.open());
            return text;
        }).setWidth(width);
    }

    public Grid.Column<B> addLinkWithPopup(String header, Function<B, String> textProvider, Function<B, Component> popupTextProvider) {
        return addComponent(header, (ValueProvider<B, Component>) bean -> {
            Dialog dialog = new Dialog();

            dialog.setCloseOnOutsideClick(false);

            NativeButton button = new NativeButton(textProvider.apply(bean));
            button.addClickListener(event -> {
                dialog.removeAll();
                dialog.add(popupTextProvider.apply(bean));
                dialog.open();
            });

            return button;
        });
    }

    /**
     * Добавляет колонку ссылку
     *
     * @deprecated Не нужно использовать этот метод, так как информация о компоненте большая и нужно использовать рендеринг на клиенте
     */
    @Deprecated
    public <K, X extends Component & HasUrlParameter<K>> Grid.Column<B> addLink(String header, ValueProvider<B, String> captionProvider,
                                                                                ValueProvider<B, K> idProvider, Class<? extends X> viewClass) {
        return grid.addComponentColumn(b -> new RouterLink(captionProvider.apply(b), viewClass, idProvider.apply(b))).setHeader(header).setResizable(true);
    }

    public <Id, T extends IdHolderWithName<Id>, X extends Component & HasUrlParameter<Id>> Grid.Column<B> addLink(
            String header, Function<B, T> function, Class<? extends X> viewClass) {
        return grid.addComponentColumn(
                b -> {
                    T idNameHolder = function.apply(b);
                    return new RouterLink(idNameHolder.getName(), viewClass, idNameHolder.getId());
                }).setHeader(header).setResizable(true);
    }

    public GridColumns<B> add(String header, ValueProvider<B, String> valueProvider) {
        addColumn(header, valueProvider);
        return this;
    }

    public GridColumns<B> addBoolean(String header, ValueProvider<B, Boolean> valueProvider) {
        addColumn(header, b -> Boolean.TRUE.equals(valueProvider.apply(b)) ? "да" : "нет");
        return this;
    }

    /**
     * Вычисляем значение в footer на основе данных грида
     */
    public GridColumns<B> footer(Grid.Column<B> column,
                                 Function<Collection<B>, String> footerFunction) {
        dataSource.addListener(value -> column.setFooter(new Label(footerFunction.apply(value))));
        return this;
    }

    public GridColumns<B> actions(SerializableBiConsumer<GridActions<B>, B> consumer) {
        createActionColumnIfNeed();
        actionsConsumers.add(consumer);
        return this;
    }

    public GridColumns<B> actionsInsert(SerializableBiConsumer<GridActions<B>, B> consumer) {
        createActionColumnIfNeed();
        actionsConsumers.add(0, consumer);
        return this;
    }

    private void createActionColumnIfNeed() {
        if (actionsConsumers.isEmpty()) {
            addComponent("Действия", b -> {
                VerticalLayout layout = new CustomVerticalLayout();
                GridActions<B> bGridActions = new GridActions<>();
                bGridActions.draw(b, grid, layout);
                for (BiConsumer<GridActions<B>, B> actionsConsumer : actionsConsumers) {
                    actionsConsumer.accept(bGridActions, b);
                }
                return layout;
            }).setWidth("140px");
        }
    }

    public Grid<B> getGrid() {
        return grid;
    }

}
