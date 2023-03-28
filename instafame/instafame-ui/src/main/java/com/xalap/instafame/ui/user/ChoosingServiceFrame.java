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
 * @author Usov Andrey
 * @since 2020-06-05
 */
@CssImport("./styles/views/choosing-service.css")
@Route(value = ChoosingServiceFrame.VIEW_NAME, layout = MainLayout.class)
public class ChoosingServiceFrame extends NavigationFrame {

    public static final String VIEW_NAME = "choosingService";

    @Autowired
    public ChoosingServiceFrame() {


/*
        Div results = new Div(
                createCard(VaadinIcon.USERS, "Подписчики", "Накрутка подписчиков на страницу"),
                createCard(VaadinIcon.USER_STAR, "Премиум подписчики", "Накрутка премиум подписчиков на страницу"),
                createCard(VaadinIcon.HEART, "Лайки", "Накрутка лайков на страницу"),
                createCard(VaadinIcon.COMMENT, "Коммментарии", "Накрутка комментариев на страницу"),
                createCard(VaadinIcon.FILM, "Просмотры", "Накрутка просмотров на страницу")
        ).className("results");*/
        Html results = new Html("<div class=\"l-wrapper\"><div class=\"l-inner\"><main class=\"l-inner__main\"><div class=\"l-inner__content \"><div class=\"row\"><div class=\"cards__col col-12 col-xxs-6 col-sm-4 col-lw-3\"><a href=\"/campaign/add/instagram/complex\" class=\"card card--complex\" data-ajax-handled=\"\"><div class=\"card__inn\"><span class=\"round-icon card__icon\"><svg class=\"icon icon-magic-wand \"><use xmlns:xlink=\"http://www.w3.org/1999/xlink\" xlink:href=\"/frontend/img/sprite.svg?5dda383805#icon-magic-wand\"></use></svg></span> <span class=\"card__name\">Комплексная накрутка</span> <p class=\"card__desc\">Автоматическое создание всех возможных типов заданий</p></div></a></div> <div class=\"cards__col col-12 col-xxs-6 col-sm-4 col-lw-3\"><a href=\"/campaign/add/instagram/friends\" class=\"card card--friends\" data-ajax-handled=\"\"><div class=\"card__inn\"><span class=\"round-icon card__icon\"><svg class=\"icon icon-users \"><use xmlns:xlink=\"http://www.w3.org/1999/xlink\" xlink:href=\"/frontend/img/sprite.svg?5dda383805#icon-users\"></use></svg></span> <span class=\"card__name\">Подписчики</span> <p class=\"card__desc\">Накрутка подписчиков на страницу</p></div></a></div> <div class=\"cards__col col-12 col-xxs-6 col-sm-4 col-lw-3\"><a href=\"/campaign/add/instagram/likes\" class=\"card card--likes\" data-ajax-handled=\"\"><div class=\"card__inn\"><span class=\"round-icon card__icon\"><svg class=\"icon icon-heart-fill \"><use xmlns:xlink=\"http://www.w3.org/1999/xlink\" xlink:href=\"/frontend/img/sprite.svg?5dda383805#icon-heart-fill\"></use></svg></span> <span class=\"card__name\">Лайки</span> <p class=\"card__desc\">Лайки на публикацию, фотографию, видеозапись, комментарий</p></div></a></div> <div class=\"cards__col col-12 col-xxs-6 col-sm-4 col-lw-3\"><a href=\"/campaign/add/instagram/comments\" class=\"card card--comments\" data-ajax-handled=\"\"><div class=\"card__inn\"><span class=\"round-icon card__icon\"><svg class=\"icon icon-comment \"><use xmlns:xlink=\"http://www.w3.org/1999/xlink\" xlink:href=\"/frontend/img/sprite.svg?5dda383805#icon-comment\"></use></svg></span> <span class=\"card__name\">Комментарии</span> <p class=\"card__desc\">Комментарии на публикацию, фотографию, видеозапись</p></div></a></div> <div class=\"cards__col col-12 col-xxs-6 col-sm-4 col-lw-3\"><a href=\"/campaign/add/instagram/video\" class=\"card card--video\" data-ajax-handled=\"\"><div class=\"card__inn\"><span class=\"round-icon card__icon\"><svg class=\"icon icon-camera \"><use xmlns:xlink=\"http://www.w3.org/1999/xlink\" xlink:href=\"/frontend/img/sprite.svg?5dda383805#icon-camera\"></use></svg></span> <span class=\"card__name\">Просмотры видео</span> <p class=\"card__desc\">Накрутка просмотров на видеозапись</p></div></a></div> <div class=\"cards__col col-12 col-xxs-6 col-sm-4 col-lw-3\"><a href=\"/campaign/add/instagram/history\" class=\"card card--history\" data-ajax-handled=\"\"><div class=\"card__inn\"><span class=\"round-icon card__icon\"><svg class=\"icon icon-docs \"><use xmlns:xlink=\"http://www.w3.org/1999/xlink\" xlink:href=\"/frontend/img/sprite.svg?5dda383805#icon-docs\"></use></svg></span> <span class=\"card__name\">Просмотры историй</span> <p class=\"card__desc\">Накрутка просмотров на истории</p></div></a></div> <div class=\"cards__col col-12 col-xxs-6 col-sm-4 col-lw-3\"><a href=\"/campaign/add/instagram/autolikes\" class=\"card card--autolikes\" data-ajax-handled=\"\"><div class=\"card__inn\"><span class=\"round-icon card__icon\"><svg class=\"icon icon-heart-fill \"><use xmlns:xlink=\"http://www.w3.org/1999/xlink\" xlink:href=\"/frontend/img/sprite.svg?5dda383805#icon-heart-fill\"></use></svg></span> <span class=\"card__name\">Автолайки</span> <p class=\"card__desc\">Автоматические лайки на новые публикации</p></div></a></div> <div class=\"cards__col col-12 col-xxs-6 col-sm-4 col-lw-3\"><a href=\"/campaign/add/instagram/autocomments\" class=\"card card--autocomments\" data-ajax-handled=\"\"><div class=\"card__inn\"><span class=\"round-icon card__icon\"><svg class=\"icon icon-comment \"><use xmlns:xlink=\"http://www.w3.org/1999/xlink\" xlink:href=\"/frontend/img/sprite.svg?5dda383805#icon-comment\"></use></svg></span> <span class=\"card__name\">Автокомментарии</span> <p class=\"card__desc\">Автоматические комментарии на новые публикации</p></div></a></div> <div class=\"cards__col col-12 col-xxs-6 col-sm-4 col-lw-3\"><a href=\"/campaign/add/instagram/autovideo\" class=\"card card--autovideo\" data-ajax-handled=\"\"><div class=\"card__inn\"><span class=\"round-icon card__icon\"><svg class=\"icon icon-camera \"><use xmlns:xlink=\"http://www.w3.org/1999/xlink\" xlink:href=\"/frontend/img/sprite.svg?5dda383805#icon-camera\"></use></svg></span> <span class=\"card__name\">Автопросмотры видео</span> <p class=\"card__desc\">Автоматические просмотры на новые видео</p></div></a></div></div></div></main></div></div>");
        //Div container = new Div(results).className("directory-container");
        Div body = new Div(results);
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
