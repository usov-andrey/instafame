/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.search;

import com.xalap.framework.utils.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Внешнее публичное api
 *
 * @author Усов Андрей
 * @since 18/01/2019
 */
@Controller
@RequestMapping(value = InstagramController.NAME)
public class InstagramController {

    public static final String NAME = "instagram";

    @Autowired
    private InstagramSearchService autocompleteService;

    @RequestMapping(method = RequestMethod.GET, value = "autocomplete")//legacy имя
    public
    @ResponseBody
    List<InstagramUserDTO> autoComplete(@RequestParam(required = false) String term,
                                        @RequestParam(required = false) String clientId) {
        if (StringHelper.isEmpty(term) || term.length() < 3) {
            return new ArrayList<>();
        }
        return autocompleteService.getUsers(escape(term), escape(clientId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "instagram")//legacy имя
    public
    @ResponseBody
    InstagramUserDTO instagramUser(@RequestParam(required = false) String term,
                                   @RequestParam(required = false) String clientId) {
        return autocompleteService.getUser(escape(term), escape(clientId));
    }

    private String escape(String value) {
        return StringHelper.isEmpty(value) ? "" : HtmlUtils.htmlEscape(value);
    }
}
