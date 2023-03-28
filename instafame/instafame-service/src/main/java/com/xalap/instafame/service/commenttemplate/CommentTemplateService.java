/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.commenttemplate;

import com.xalap.framework.data.CrudService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 08/05/2019
 */
@Service
public class CommentTemplateService extends CrudService<CommentTemplateBean, CommentTemplateRepository, Integer> {

    public static final String COMMENT_TEMPLATES = "commentTemplates";

    public CommentTemplateService() {
        super();
    }

    /**
     * Отсортированные
     */
    @Cacheable(COMMENT_TEMPLATES)
    public List<CommentTemplateBean> getAllSorted() {
        List<CommentTemplateBean> all = super.getAll();
        all.sort((o1, o2) -> o2.getComText().compareTo(o1.getComText()));
        return all;
    }

    @Override
    protected void onChange(CommentTemplateBean bean) {
        evictAll(COMMENT_TEMPLATES);
    }
}
