/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer;

import com.vaadin.flow.function.ValueProvider;
import com.xalap.vaadin.custom.grid.renderer.core.RendererBuilder;

/**
 * @author Usov Andrey
 * @since 2020-05-04
 */
public class IfRendererBuilder<T> extends RendererBuilder<T> {

    public IfRendererBuilder(ValueProvider<T, Boolean> condProvider, RendererBuilder<T> item) {
        super("<template is=\"dom-if\" if=\"{{item.cond}}\">%s</template>", item.getTemplate());
        withProperty("cond", condProvider);
        withRendererBuilders(item);
    }

    private IfRendererBuilder(ValueProvider<T, Boolean> condProvider, RendererBuilder<T> ifItem, RendererBuilder<T> elseItem) {
        super("<template is=\"dom-if\" if=\"{{item.ifCond}}\">%s</template>" +
                "<template is=\"dom-if\" if=\"{{item.elseCond}}\">%s</template>", ifItem.getTemplate(), elseItem.getTemplate());
        withProperty("ifCond", condProvider);
        withProperty("elseCond", t -> !condProvider.apply(t));
        withRendererBuilders(ifItem, elseItem);
    }

    public static <T> IfRendererBuilder<T> ifBuilder(ValueProvider<T, Boolean> condProvider, RendererBuilder<T> builder) {
        return new IfRendererBuilder<>(condProvider, builder);
    }

    public static <T> IfRendererBuilder<T> ifElseBuilder(ValueProvider<T, Boolean> condProvider,
                                                         RendererBuilder<T> ifBuilder, RendererBuilder<T> elseBuilder) {
        return new IfRendererBuilder<>(condProvider, ifBuilder, elseBuilder);
    }
}
