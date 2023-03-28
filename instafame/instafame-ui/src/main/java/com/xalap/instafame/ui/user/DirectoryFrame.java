/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.user;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.xalap.vaadin.custom.component.fluent.Div;
import com.xalap.vaadin.custom.frame.NavigationFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Создать заказ
 *
 * @author Usov Andrey
 * @since 2020-05-09
 */
@CssImport("./styles/views/directory.css")
@Route(value = DirectoryFrame.VIEW_NAME, layout = MainLayout.class)
public class DirectoryFrame extends NavigationFrame {

    public static final String VIEW_NAME = "directory";

    @Autowired
    public DirectoryFrame() {


/*
        Div results = new Div(
                createCard(VaadinIcon.USERS, "Подписчики", "Накрутка подписчиков на страницу"),
                createCard(VaadinIcon.USER_STAR, "Премиум подписчики", "Накрутка премиум подписчиков на страницу"),
                createCard(VaadinIcon.HEART, "Лайки", "Накрутка лайков на страницу"),
                createCard(VaadinIcon.COMMENT, "Коммментарии", "Накрутка комментариев на страницу"),
                createCard(VaadinIcon.FILM, "Просмотры", "Накрутка просмотров на страницу")
        ).className("results");*/
        Html results = new Html("<div class=\"directory-container\"><div class=\"directory-title\"><h1>Vaadin Directory</h1><h2>Curated open source web components - with or without Java.</h2></div><div class=\"search__components-cta-section__container\"><a router-link=\"\" href=\"directory/my-components\"><div class=\"my-components-section\">My components</div></a><a router-link=\"\" href=\"directory/my-components?uploadNewComponent=\"><div class=\"publish-section\">Publish component</div></a></div><div class=\"search__content-section\"><div class=\"results\"><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/alumps-vaadin-rich-text-editor\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://vaadin.com/images/directory/addon-icon-default.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">alump's Vaadin Rich Text Editor</div><div class=\"component__content__author\">by Sami Viitanen</div></div><div class=\"component__content__summary\">Yet another fork of Vaadin Rich Text Editor with some additional features</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"June 3, 2020\">Updated 13 hours ago</div><div class=\"component__content__comments\">0</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/so-charts\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://static.vaadin.com/directory/user52447/icon/file7893266372528801097_1590849176682so-charts.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">SO Charts</div><div class=\"component__content__author\">by Syam Pillai</div></div><div class=\"component__content__summary\">A wrapper around the \"echarts\" JavaScript library for creating charts.</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"June 3, 2020\">Updated 15 hours ago</div><div class=\"component__content__comments\">0</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/so-components\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--miscellaneous\">Miscellaneous</div><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://static.vaadin.com/directory/user52447/icon/file6453361951825290194_1554467029102so-logo.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">SO Components</div><div class=\"component__content__author\">by Syam Pillai</div></div><div class=\"component__content__summary\">A set of Vaadin Flow Components and Abstractions</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"June 3, 2020\">Updated 15 hours ago</div><div class=\"component__content__comments\">2</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/xterm-console-addon\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--miscellaneous\">Miscellaneous</div><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://static.vaadin.com/directory/user79257/icon/file2241997754796180436_1580156695241xterm-logo-vaadin.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">XTerm Console Addon</div><div class=\"component__content__author\">by Flowing Code S.A.</div></div><div class=\"component__content__summary\">Vaadin 14+ Java integration of xterm.js terminal emulator.</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"June 3, 2020\">Updated 17 hours ago</div><div class=\"component__content__comments\">6</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/grid-pagination\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--miscellaneous\">Miscellaneous</div><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__tag component__tag--official_vaadin\">Official Vaadin</div><div class=\"component__tag component__tag--data\">Data</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://vaadin.com/images/directory/addon-icon-default.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">Grid Pagination</div><div class=\"component__content__author\">by Klaudeta Mertiku</div></div><div class=\"component__content__summary\">Custom Vaadin 14+ Grid component with pagination feature enabled</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"June 3, 2020\">Updated 19 hours ago</div><div class=\"component__content__comments\">15</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/mediaquery\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__tag component__tag--themes\">Themes</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://vaadin.com/images/directory/addon-icon-default.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">MediaQuery</div><div class=\"component__content__author\">by Jean-Christophe Gueriaud</div></div><div class=\"component__content__summary\">Simple extension to bind CSS Media query in Java, help to build responsive ...</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"June 2, 2020\">Updated 2 days ago</div><div class=\"component__content__comments\">6</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/full-calendar-4-scheduler-web-component\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://static.vaadin.com/directory/user109101/icon/file4578665484145860671_1569482789799scheduler.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">Full Calendar 4 Scheduler web component</div><div class=\"component__content__author\">by Stefan Uebe</div></div><div class=\"component__content__summary\">Full Calendar 4 Scheduler web component for the Vaadin Framework 14+</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"June 2, 2020\">Updated 2 days ago</div><div class=\"component__content__comments\">3</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/full-calendar-4-web-component\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://static.vaadin.com/directory/user109101/icon/file1122875421528301012_1569481654167calendar.PNG\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">Full Calendar 4 web component</div><div class=\"component__content__author\">by Stefan Uebe</div></div><div class=\"component__content__summary\">Integration of Full Calendar 4.x for the Vaadin Framework 14+</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"June 2, 2020\">Updated 2 days ago</div><div class=\"component__content__comments\">13</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/superfields\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://static.vaadin.com/directory/user18572/icon/file9053317322677050388_1587233669185Note-Memo-01-376.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">SuperFields</div><div class=\"component__content__author\">by Miki Olszewski</div></div><div class=\"component__content__summary\">Localised number fields, date pickers with date pattern, two configurable s...</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"May 29, 2020\">Updated 6 days ago</div><div class=\"component__content__comments\">4</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/textfield-formatter\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://static.vaadin.com/directory/user6255/icon/file3116410080545579252_1558819834942logo.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">TextField Formatter</div><div class=\"component__content__author\">by Johannes Häyry</div></div><div class=\"component__content__summary\">Cleave.js based client-side text formatter for Vaadin 8 and Vaadin 14</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"May 29, 2020\">Updated 6 days ago</div><div class=\"component__content__comments\">19</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/async-manager\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--tools\">Tools</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://vaadin.com/images/directory/addon-icon-default.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">Async Manager</div><div class=\"component__content__author\">by Artem Godin</div></div><div class=\"component__content__summary\">Advanced Push/Polling manager for Vaadin Flow</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"May 29, 2020\">Updated 6 days ago</div><div class=\"component__content__comments\">5</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/tooltips4vaadin\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://static.vaadin.com/directory/user324503/icon/file6201722200077957334_1567719717797Tooltips4Vaadin.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">Tooltips4Vaadin</div><div class=\"component__content__author\">by Gerrit Sedlaczek</div></div><div class=\"component__content__summary\">A Tippy.js based Tooltip-Plugin for Vaadin Flow (14+).</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"May 28, 2020\">Updated 7 days ago</div><div class=\"component__content__comments\">12</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/ag-grid-flow\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__tag component__tag--official_vaadin\">Official Vaadin</div><div class=\"component__tag component__tag--data\">Data</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://static.vaadin.com/directory/user142257/icon/file4201382366063702685_1590669036506icon_vcf.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">ag-grid-flow</div><div class=\"component__content__author\">by Vaadin   ComponentFactory</div></div><div class=\"component__content__summary\">Flow wrapper for Ag-grid Js Component</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"May 28, 2020\">Updated 7 days ago</div><div class=\"component__content__comments\">0</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/ckeditorvaadin\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://static.vaadin.com/directory/user67580/icon/file3636779217081899773_1585735997200vaadin-ckeditor.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">CKEditorVaadin</div><div class=\"component__content__author\">by Ryan Pang</div></div><div class=\"component__content__summary\">CKEditor 5 and Vaadin 14</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"May 28, 2020\">Updated 1 week ago</div><div class=\"component__content__comments\">6</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/paged-tabs\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://static.vaadin.com/directory/user1373/icon/file6866864378162632244_1590604003465Screenshot2020-05-27at13.26.29.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">paged-tabs</div><div class=\"component__content__author\">by Alejandro Duarte</div></div><div class=\"component__content__summary\">A tabs component to show a page when a tab is clicked</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"May 27, 2020\">Updated 1 week ago</div><div class=\"component__content__comments\">9</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/enhanced-crud\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__tag component__tag--official_vaadin\">Official Vaadin</div><div class=\"component__tag component__tag--data\">Data</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://static.vaadin.com/directory/user142257/icon/file634757131751580020_1585743546513factory.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">Enhanced Crud</div><div class=\"component__content__author\">by Vaadin   ComponentFactory</div></div><div class=\"component__content__summary\">Enhanced CRUD offers read only mode, canceling save and access to Editor bu...</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"May 27, 2020\">Updated 1 week ago</div><div class=\"component__content__comments\">2</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/exampledata\"><div class=\"component__content__tags\"><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://vaadin.com/images/directory/addon-icon-default.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">exampledata</div><div class=\"component__content__author\">by Artur Signell</div></div><div class=\"component__content__summary\">Example data generator</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"May 27, 2020\">Updated 1 week ago</div><div class=\"component__content__comments\">0</div></div></a></div></div><div class=\"component\"><div class=\"component__content\"><a router-link=\"true\" href=\"directory/component/google-maps-addon\"><div class=\"component__content__tags\"><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://static.vaadin.com/directory/user79257/icon/file3376385963048784655_1590496580311google-maps-vaadin.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">Google Maps Addon</div><div class=\"component__content__author\">by Flowing Code S.A.</div></div><div class=\"component__content__summary\">Vaadin 14+ addon for Google Maps Web Component</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"May 26, 2020\">Updated 1 week ago</div><div class=\"component__content__comments\">2</div></div></a></div></div></div><vc-pagination></vc-pagination></div></div>");
        //Div container = new Div(results).className("directory-container");
        Div body = new Div(results).className("directory-base");
        setViewContent(body);
    }

    private Component createCard(VaadinIcon icon, String header, String text) {
        /*
        <div class="cards__col col-12 col-xxs-6 col-sm-4 col-lw-3">
   <a href="/campaign/add/instagram/friends" class="card card--friends" data-ajax-handled="">
      <div class="card__inn">
         <span class="round-icon card__icon">
            <svg class="icon icon-users">
               <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="/frontend/img/sprite.svg?112fa931c0#icon-users"></use>
            </svg>
         </span>
         <span class="card__name">Подписчики</span>
         <p class="card__desc">Накрутка подписчиков на страницу</p>
      </div>
   </a>
</div> */

        RouterLink routerLink = new RouterLink(null, DirectoryFrame.class);
        //routerLink.addClassNames("card", "card--friends");
        //routerLink.add(new Html("<div><div class=\"component__content__tags\"><div class=\"component__tag component__tag--ui\">UI</div><div class=\"component__content__framework vaadin\"></div></div><div class=\"component__content__basic-info\"><div class=\"component__content__image-container\"><img class=\"component__content__icon\" src=\"https://vaadin.com/images/directory/addon-icon-default.png\"><cd-rating-stars class=\"component__content__rating\"></cd-rating-stars></div><div class=\"component__content__info\"><div class=\"component__content__title\">alump's Vaadin Rich Text Editor</div><div class=\"component__content__author\">by Sami Viitanen</div></div><div class=\"component__content__summary\">Yet another fork of Vaadin Rich Text Editor with some additional features</div></div><div class=\"component__content__extra-info\"><div class=\"component__content__update-info\" title=\"June 3, 2020\">Updated 12 hours ago</div><div class=\"component__content__comments\">0</div></div><div>"));
        /*
        routerLink.add(
                div("card__inn",
                        span("round-icon card__icon",
                                icon.create()
                        ),
                        span("card__name", header),
                        paragraph("card__desc", text)
                ));*/
        Div card = new Div(routerLink).className("component__content");
        return card;
    }

    @Override
    protected String getTitle() {
        return "Пополнение баланса";
    }
}
