/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component;

import com.xalap.vaadin.custom.component.detailsdrawer.EmptyDetailsDrawerFooter;
import com.xalap.vaadin.custom.data.DataChangeListener;
import com.xalap.vaadin.custom.data.DataHolder;
import com.xalap.vaadin.starter.ui.components.detailsdrawer.DetailsDrawer;
import com.xalap.vaadin.starter.ui.components.detailsdrawer.DetailsDrawerHeader;

import java.io.Serializable;

/**
 * Окно состоящее из трех частей: header, content, footer
 *
 * @author Usov Andrey
 * @since 30/01/2020
 */
public abstract class DetailsWindow<BEAN extends Serializable> implements Serializable {

    private final DataHolder<BEAN> beanHolder = new DataHolder<>();
    protected DetailsDrawer detailsDrawer;
    protected DetailsDrawerHeader detailsDrawerHeader;
    protected EmptyDetailsDrawerFooter detailsDrawerFooter;

    public void showDetails(BEAN bean) {
        beanHolder.setValue(bean);
        showDrawer();
    }

    /**
     * Добавить какие-то действия, которыу будут выполняться, когда будут заданы данные для этого окна
     */
    protected void addBeanListener(DataChangeListener<BEAN> listener) {
        beanHolder.addListener(listener);
    }

    /**
     * Показать компонент
     */
    protected void showDrawer() {
        detailsDrawer.show();
    }

    protected void hideDrawer() {
        detailsDrawer.hide();
    }

    public DetailsDrawer createDetailsDrawer() {
        detailsDrawer = newDetailsDrawer();

        // Header
        detailsDrawerHeader = new DetailsDrawerHeader("");
        detailsDrawerHeader.addCloseListener(buttonClickEvent -> hideDrawer());
        detailsDrawer.setHeader(detailsDrawerHeader);

        // Footer
        detailsDrawerFooter = new EmptyDetailsDrawerFooter();
        detailsDrawer.setFooter(detailsDrawerFooter);

        return detailsDrawer;
    }

    public DetailsDrawer createDetailsDrawerWithoutHeader() {
        createDetailsDrawer();
        hideHeader();
        return detailsDrawer;
    }

    public void hideHeader() {
        detailsDrawerHeader.setVisible(false);
    }

    protected DetailsDrawer newDetailsDrawer() {
        return new DetailsDrawer(DetailsDrawer.Position.RIGHT);
    }

}
