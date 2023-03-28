/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer;

import com.xalap.vaadin.custom.grid.renderer.core.RendererBuilder;

/**
 * @author Usov Andrey
 * @since 2020-05-07
 */
public class RepeaterRendererBuilder<T> extends RendererBuilder<T> {

    public RepeaterRendererBuilder(RendererBuilder<T> rendererBuilder) {
        super("<template is=\"dom-repeat\" items=\"{{items}}\">%s</template>", rendererBuilder.getTemplate());
        withRendererBuilders(rendererBuilder);
    }

}
