/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.vaadin.starter.ui.components.FlexBoxLayout;

/**
 * A view frame that establishes app design guidelines. It consists of four
 * parts:
 * <ul>
 * <li>Topmost {@link #setViewHeader(Component...) header}</li>
 * <li>Center {@link #setViewContent(Component...) content}</li>
 * <li>Center {@link #setViewDetails(Component...) details}</li>
 * <li>Bottom {@link #setViewFooter(Component...) footer}</li>
 * </ul>
 */
@CssImport("./styles/components/view-frame.css")
public class SplitViewFrame extends Composite<Div> implements HasStyle {

    private final String CLASS_NAME = "view-frame";

    private final Div header;

    private final FlexBoxLayout wrapper;
    private final Div content;
    private final Div details;

    private final Div footer;

    public SplitViewFrame() {
        setClassName(CLASS_NAME);

        header = new Div();
        header.setClassName(CLASS_NAME + "__header");

        wrapper = new FlexBoxLayout();
        wrapper.setClassName(CLASS_NAME + "__wrapper");

        content = new Div();
        content.setClassName(CLASS_NAME + "__content");

        details = new Div();
        details.setClassName(CLASS_NAME + "__details");

        footer = new Div();
        footer.setClassName(CLASS_NAME + "__footer");

        wrapper.add(content, details);
        getContent().add(header, wrapper, footer);
    }

    /**
     * Sets the header slot's components.
     */
    public void setViewHeader(Component... components) {
        header.removeAll();
        header.add(components);
    }

    /**
     * Sets the content slot's components.
     */
    public void setViewContent(Component... components) {
        content.removeAll();
        content.add(components);
    }

    /**
     * Sets the detail slot's components.
     */
    public void setViewDetails(Component... components) {
        details.removeAll();
        details.add(components);
    }

    public void setViewDetailsPosition(Position position) {
        if (position.equals(Position.RIGHT)) {
            wrapper.setFlexDirection(FlexLayout.FlexDirection.ROW);

        } else if (position.equals(Position.BOTTOM)) {
            wrapper.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        }
    }

    /**
     * Sets the footer slot's components.
     */
    public void setViewFooter(Component... components) {
        footer.removeAll();
        footer.add(components);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        MainLayout.get().getAppBar().reset();
    }

    public enum Position {
        RIGHT, BOTTOM
    }
}
