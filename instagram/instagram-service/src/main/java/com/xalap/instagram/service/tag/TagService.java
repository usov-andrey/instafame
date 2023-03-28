/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.tag;

import com.xalap.framework.utils.StringHelper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author Усов Андрей
 * @since 20.05.17
 */
@Service
public class TagService {

    public static final String TAG = "#";
    public static final String PEOPLE = "@";

    /**
     * @return Набор тегов из текста
     */
    public Set<String> extractTags(String... text) {
        Set<String> result = new HashSet<>();
        extractTags(result, text);
        return result;
    }

    public void extractTags(Set<String> tagSet, String... text) {
        for (String textEl : text) {
            processTags(textEl, (e) -> tagSet.add(e.toLowerCase()));
        }
    }

    /**
     * Из текста получаем набор хэштегов
     */
    private void processTags(String text, Consumer<String> consumer) {
        processExpr(text, TAG, consumer);
    }

    public void processPeoples(String text, Consumer<String> consumer) {
        processExpr(text, PEOPLE, consumer);
    }

    private void processExpr(String text, String start, Consumer<String> consumer) {
        processExpr(text, start, (s, integer) -> consumer.accept(s));
    }

    private void processExpr(String text, String start, BiConsumer<String, Integer> consumer) {
        try {
            //Не нашел я нормального регулярного выражения на тэги в инстаграм, делаем свой парсер
            String[] array = text.split(Pattern.quote(start));
            int pos = 0;
            for (int i = 0; i < array.length; i++) {
                if (i > 0) {//Первый элемент пропускаем
                    String value = array[i];
                    if (StringHelper.isNotEmpty(value)) {
                        //Ищем пробел и берем все что до него
                        int spaceIndex = endIndex(value);
                        if (spaceIndex > -1) {
                            value = value.substring(0, spaceIndex);
                        }
                        if (StringHelper.isNotEmpty(value)) {
                            consumer.accept(value, pos);
                        }
                    }
                    pos += start.length() + array[i].length();//Именно array[i], потому что value меняется
                } else {
                    pos = pos + array[i].length();
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error on processExpr:text=" + text + " start=" + start, e);
        }
    }

    public String replace(String text, String start, Function<String, String> replaceFunction) {
        StringBuilder sb = new StringBuilder();
        AtomicInteger lastPos = new AtomicInteger();
        lastPos.set(0);
        processExpr(text, start, (s, pos) -> {
            sb.append(text, lastPos.get(), pos);
            sb.append(replaceFunction.apply(s));
            lastPos.set(pos + s.length() + start.length());
        });
        sb.append(text.substring(lastPos.get()));
        return sb.toString();
    }

    private int endIndex(String value) {
        List<Integer> indexes = new ArrayList<>();
        indexes.add(index(value, " "));
        indexes.add(index(value, StringHelper.END_LINE_R));
        indexes.add(index(value, StringHelper.END_LINE));
        Collections.sort(indexes);
        int res = indexes.get(0);
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    private int index(String value, String sep) {
        int index = value.indexOf(sep);
        return index == -1 ? Integer.MAX_VALUE : index;
    }

    /**
     * Выводим теги в виде строки
     */
    public String formatTags(Collection<String> tags) {
        return StringHelper.join(tags, s -> "#" + s, " ");
    }


}
