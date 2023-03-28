/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.frame;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.shared.Registration;
import com.xalap.vaadin.starter.ui.views.SplitViewFrame;

/**
 * SplitViewFrame + возможность добавить обработчик при измении ширины экрана
 *
 * @author Usov Andrey
 * @since 18.05.2021
 */
public class ResizableFrame extends SplitViewFrame {
    public static final int MOBILE_BREAKPOINT = 480;
    private Registration resizeListener;

    /**
     * @param width ширина в пикселях
     */
    protected void updateFrameForWidth(int width) {

    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        getUI().ifPresent(ui -> {
            Page page = ui.getPage();
            resizeListener = page.addBrowserWindowResizeListener(event -> updateFrameForWidth(event.getWidth()));
            page.retrieveExtendedClientDetails(details -> updateFrameForWidth(details.getBodyClientWidth()));
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        resizeListener.remove();
        super.onDetach(detachEvent);
    }
}
