/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.entity;

import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.function.SerializableRunnable;
import com.xalap.vaadin.custom.component.MenuButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Кастомные действия на форме
 *
 * @author Usov Andrey
 * @since 2020-05-07
 */
public class EntityFormActions<B extends Serializable> implements Serializable {

    private SerializableBiConsumer<Actions, B> provider = (actions, bean) -> {
    };

    Optional<MenuButton> createButton(B bean) {
        MenuButton button = new MenuButton("Действия");
        Actions actionList = new Actions();
        provider.accept(actionList, bean);
        if (actionList.isEmpty()) {
            return Optional.empty();
        }
        for (Action action : actionList.values) {
            button.addMenu(action.caption, action.runnable);
        }
        return Optional.of(button);
    }

    public void setProvider(SerializableBiConsumer<Actions, B> provider) {
        this.provider = provider;
    }

    public static class Actions {
        private final List<Action> values = new ArrayList<>();

        public boolean isEmpty() {
            return values.isEmpty();
        }

        public Actions add(String caption, SerializableRunnable consumer) {
            values.add(new Action(caption, consumer));
            return this;
        }
    }

    static class Action {
        String caption;
        SerializableRunnable runnable;

        Action(String caption, SerializableRunnable runnable) {
            this.caption = caption;
            this.runnable = runnable;
        }

        public String getCaption() {
            return caption;
        }

        public Runnable getRunnable() {
            return runnable;
        }
    }
}
