/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task;

import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.instaorder.IOBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Создаем задачи в заказе по заданным коммментариям
 *
 * @author Usov Andrey
 * @since 18.06.2021
 */
public class CreateCommentTasks {

    private static final String INSTAGRAM_URL = "instagram.com/";

    public void create(IOBean ioBean, String comments) {

    }

    public List<UrlAndMedias> parse(String comments) {
        List<UrlAndMedias> result = new ArrayList<>();
        UrlAndMedias current = null;
        for (String textByLine : getTextByLines(comments)) {
            if (textByLine.contains(INSTAGRAM_URL)) {
                current = new UrlAndMedias(textByLine);
                result.add(current);
            } else if (current != null) {
                current.addComment(textByLine);
            } else {
                throw new IllegalStateException("Error on parse comments:" + comments);
            }
        }
        return result;
    }

    /**
     * @return Разбиваем текст на строки
     */
    public List<String> getTextByLines(String text) {
        String[] lines = text.split(StringHelper.END_LINE);
        if (lines.length == 0) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            String trimmedLine = line.trim().replace("\r", "");
            if (!trimmedLine.isEmpty()) {
                result.add(trimmedLine);
            }
        }
        return result;
    }

    public class UrlAndMedias {
        private final String url;
        private final List<String> comments = new ArrayList<>();

        public UrlAndMedias(String url) {
            this.url = url;
        }

        public void addComment(String comment) {
            comments.add(comment);
        }

        public String getUrl() {
            return url;
        }

        public List<String> getComments() {
            return comments;
        }
    }
}
