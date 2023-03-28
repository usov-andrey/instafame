/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.ui.user;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.IOUtils;
import com.xalap.instagram.service.user.InstagramUserBean;
import com.xalap.instagram.service.user.InstagramUserService;
import com.xalap.vaadin.custom.component.InstagramComponent;
import com.xalap.vaadin.custom.dialog.FileUploadDialog;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xalap.framework.domain.holder.IdHolder.COLUMN_ID;

/**
 * Список инстаграм аккаунтов
 *
 * @author Усов Андрей
 * @since 28/06/2019
 */
@Route(value = InstagramUserListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Инстаграм пользователи")
public class InstagramUserListFrame extends RootEntityListFrame<InstagramUserBean> {

    public static final String VIEW_NAME = "instaUserList";

    @Autowired
    public InstagramUserListFrame(ServiceRef<InstagramUserService> service) {
        super(service, GridDefaultSorting.desc(COLUMN_ID));
        GridColumns<InstagramUserBean> columns = gridPanel.columns();
        columns.addLink("Время обновления", userBean -> DateHelper.getDateTime(userBean.updateTime()),
                InstagramUserBean::getId, InstagramUserFrame.class);
        columns.addComponent("Профиль", userBean -> {
            Image image = new Image(userBean.getProfilePicture(), "");
            image.setHeight("100px");
            return image;
        });
        columns.addComponent("Инстаграм", user -> new InstagramComponent(user.getUserName()));
        columns.add(InstagramUserBean.FULL_NAME);
        columns.addText("BIO", InstagramUserBean::getBiography, "350px", "100px");
        columns.add("Статы", userBean -> userBean.getUserStats().stats());
        gridPanel.buttons().add("Импорт", () -> FileUploadDialog.open(buffer -> {
            String html = IOUtils.toString(buffer.getInputStream());
            InstagramUserBean userBean = service.get().readAndUpdateUserFromHtml(html);
            navigate(InstagramUserFrame.class, userBean.getId());
        }));
    }
}
