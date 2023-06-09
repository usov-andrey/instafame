/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.components;

import com.vaadin.flow.component.charts.model.DataSeriesItem;

public class DataSeriesItemWithRadius extends DataSeriesItem {

    private String radius;
    private String innerRadius;

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
        makeCustomized();
    }

    public String getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(String innerRadius) {
        this.innerRadius = innerRadius;
        makeCustomized();
    }
}
