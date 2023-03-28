/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.web.url;

import com.xalap.framework.utils.StringHelper;
import com.xalap.framework.utils.WebHelper;

import java.util.List;
import java.util.Map;

/**
 * @author Usov Andrey
 * @since 2020-10-17
 */
public class RelativeUrl {

    private final List<String> segments;
    private final Map<String, List<String>> parametersMap;

    public RelativeUrl(List<String> segments, Map<String, List<String>> parametersMap) {
        this.segments = segments;
        this.parametersMap = parametersMap;
    }

    /**
     * @return null, если параметр не найден
     */
    public String get(String name) {
        List<String> strings = getList(name);
        if (strings == null) {
            return null;
        } else if (strings.size() > 1) {
            throw new IllegalStateException("For parameter " + name + " found multiple values:" + strings.size());
        }
        return WebHelper.decodeURL(strings.get(0));
    }

    /**
     * @return null, если параметр не найден
     */
    private List<String> getList(String name) {
        return parametersMap.get(name);
    }

    /**
     * @return null, если параметр не найден
     */
    public Integer getInteger(String name) {
        String s = get(name);
        if (StringHelper.isNotEmpty(s)) {
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public List<String> getSegments() {
        return segments;
    }

    public boolean contains(String name) {
        return parametersMap.containsKey(name);
    }
}
