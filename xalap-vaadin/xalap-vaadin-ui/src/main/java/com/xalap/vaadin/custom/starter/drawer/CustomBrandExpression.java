/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.starter.drawer;

import com.xalap.vaadin.starter.ui.components.navigation.drawer.BrandExpression;

/**
 * @author Usov Andrey
 * @since 2020-05-08
 */
class CustomBrandExpression extends BrandExpression {

    CustomBrandExpression() {
        super("");
    }

    public void changeLogoSrc(String logoImgPath) {
        logo.setSrc(logoImgPath);
    }

    public void changeTitle(String titleText) {
        title.setText(titleText);
    }

}
