/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.media;

import com.xalap.framework.utils.RandomUtils;
import com.xalap.framework.utils.StringHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Усов Андрей
 * @since 22.05.2018
 */
public class MediaCaptionTransformer {

    private static final String INVISIBLE_CHAR = "⠀";
    private static final String SPACE_CHAR = " ";
    private static final String RETURN_CHAR = "\n";
    private static final String DOT_CHAR = ".";

    private static final LetterMap SUB_CHAR_MAP = new LetterMap() {{
        put("a", "а");
        put("A", "А", "Α");
        put("c", "с");
        put("C", "С", "Ϲ");
        put("e", "е");
        put("E", "Е", "Ε");
        put("o", "о");
        put("O", "О", "Ο");
        put("p", "р");
        put("P", "Р", "Ρ");
        put("x", "х");
        put("X", "Х");
        put("B", "В", "Β");
        put("y", "у");
        put("T", "Т", "Τ");
        put("H", "Н", "Η");
        put("X", "Х", "Χ");
        put("K", "К", "Κ");
        put("M", "М", "Μ");
        //Греческий + русский
        put("Π", "П");
        put("Г", "Γ");
        //Греческий + английски
        put("Ζ", "Z");
        put("Ι", "I");
        put("Ν", "N");
        put("Y", "Υ");
    }};

    /**
     * Генерируем случайный текст
     */
    public String generateRandomText(String text) {
        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            String ch = String.valueOf(text.charAt(i));
            if (SUB_CHAR_MAP.containsKey(ch)) {
                if (RandomUtils.nextInt(1) == 1) {
                    ch = SUB_CHAR_MAP.get(ch).iterator().next();
                }
            }
            sb.append(ch);
        }
        if (text.equals(sb.toString())) {
            System.out.println("caption одинаковый:" + text);
        }
        return sb.toString();
    }

    /**
     * Количество разных комбинаций текста
     */
    public long randomCount(String text) {
        long count = 0;
        for (int i = 0; i < text.length(); i++) {
            String ch = String.valueOf(text.charAt(i));
            if (SUB_CHAR_MAP.containsKey(ch)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Корректируем абзацы.
     * Убираем пробелы перед переходом строки.
     * Заменяем точку + переход строки на специальный невидимый символ + переход строки.
     */
    public String correctIndent(String text) {
        text = replaceSpaceBeforeNewLine(text);//Убираем пробел перед переходом строки
        text = StringHelper.replace(text, DOT_CHAR + RETURN_CHAR, INVISIBLE_CHAR + RETURN_CHAR);
        return text;
    }

    private String replaceSpaceBeforeNewLine(String text) {
        //Из-за того, что я не силен в регулярке, мы делаем это
        int index = text.indexOf(SPACE_CHAR + RETURN_CHAR);
        while (index > -1) {
            text = StringHelper.replace(text, SPACE_CHAR + RETURN_CHAR, RETURN_CHAR);
            index = text.indexOf(SPACE_CHAR + RETURN_CHAR);
        }
        return text;
    }

    private static class LetterMap extends HashMap<String, Set<String>> {

        public void put(String value1, String value2, String value3) {
            put(value1, value2);
            put(value1, value3);
            put(value2, value3);
        }

        public void put(String key, String value) {
            if (key.equals(value)) {
                throw new IllegalStateException("Letters is equals:" + key);
            }
            createOrGet(key).add(value);
            createOrGet(value).add(key);
        }

        public Set<String> createOrGet(String key) {
            if (!containsKey(key)) {
                put(key, new HashSet<String>());
            }
            return get(key);
        }
    }
}
