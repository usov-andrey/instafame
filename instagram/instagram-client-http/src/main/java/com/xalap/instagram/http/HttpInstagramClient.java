/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http;

import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.json.JsonConvertingException;
import com.xalap.framework.json.JsonService;
import com.xalap.framework.logging.HasLog;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instagram.api.InstagramUserHtmlReader;
import com.xalap.instagram.api.media.Media;
import com.xalap.instagram.api.media.MediaType;
import com.xalap.instagram.api.user.User;
import com.xalap.instagram.http.model.MediaWithLastActivity;
import com.xalap.instagram.http.responsemodel.mainpage.MainPage;
import com.xalap.instagram.http.responsemodel.media.MediaResponse;
import com.xalap.instagram.http.responsemodel.profile.ProfilePage;
import com.xalap.instagram.http.responsemodel.profile.ThumbnailResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Работа с инстаграм через http запросы
 *
 * @author Usov Andrey
 * @since 2020-08-29
 */
@Service
public class HttpInstagramClient implements HasLog, InstagramUserHtmlReader {

    private static final String mediaUrl = "p/%s/";
    private static final String mediaJsonUrl = "p/%s/?__a=1";
    protected static String baseUrl = "https://www.instagram.com/";

    private final JsonService jsonService;
    private final HttpProxyService proxyService;


    public HttpInstagramClient(JsonService jsonService, HttpProxyService proxyService) {
        this.jsonService = jsonService;
        this.proxyService = proxyService;
    }

    public MediaWithLastActivity getMedia(String code) throws ServiceTemporaryException {
        String url = String.format(baseUrl + mediaJsonUrl, code);
        return proxyService.execWithProxy(url, this::readMediaFromJson);
    }

    /**
     * Получение информации о media по json
     */
    public MediaWithLastActivity readMediaFromJson(String json) {
        MediaResponse mediaResponse = jsonService.getJson(json, MediaResponse.class);
        return new MediaWithLastActivity(mediaResponse.getGraphql().getShortcodeMedia());
    }

    /**
     * Получение информации о пользователе
     */
    public User getUser(String userName) throws ServiceTemporaryException {
        String url = baseUrl + userName;
        return proxyService.execWithProxy(url, this::readUserFromHtml);
    }

    /**
     * Прочитать информацию о пользователе по html
     *
     * @param html - html код страницы
     */
    public User readUserFromHtml(String html) {
        MainPage mainPage = getMainPage(html);
        List<ProfilePage> profilePage = mainPage.getEntryData().getProfilePage();
        if (profilePage != null && !profilePage.isEmpty()) {
            ProfilePage profilePageEntry = profilePage.get(0);
            return createUser(profilePageEntry.getGraphql().getUser());
        } else {
            throw new ProfilePageEmptyException("ProfilePage is empty:" + html);
        }
    }

    private User createUser(com.xalap.instagram.http.responsemodel.profile.User user) {
        User result = new User();
        if (user == null) {
            throw new IllegalStateException("Error on read user from instagram");
        }
        result.setId(user.getId());
        result.setUserName(user.getUsername());
        result.setBiography(user.getBiography());
        result.setFullName(user.getFullName());
        result.setFollowsCount(user.getEdgeFollow().getCount());
        result.setFollowedByCount(user.getEdgeFollowedBy().getCount());
        result.setMediaCount(user.getEdgeOwnerToTimelineMedia().getCount());
        result.setPrivate(user.getIsPrivate());
        result.setLastMedias(new ArrayList<>());
        result.getLastMedias().addAll(user.getEdgeOwnerToTimelineMedia().getEdges().stream().map(
                edge ->  createMedia(edge.getNode())).collect(Collectors.toList()));
        result.setProfilePicture(user.getProfilePicUrl());
        result.setExternalLink(user.getExternalUrl());
        return result;
    }

    private Media createMedia(com.xalap.instagram.http.responsemodel.profile.Node media) {
        Media result = new Media();
        result.setId(media.getId());
        result.setSrc(media.getDisplayUrl());
        result.setCode(media.getShortcode());
        result.setType(media.getIsVideo() ? MediaType.video : MediaType.image);
        result.setVideoUrl(media.getVideoUrl());
        result.setCaption(media.getEdgeMediaToCaption().getEdges().isEmpty() ? "" : media.getEdgeMediaToCaption().getEdges().get(0).getNode().getText());
        result.setLikesCount(media.getEdgeLikedBy().getCount());
        result.setCommentsCount(media.getEdgeMediaToComment().getCount());
        result.setCreateTime(new Date(media.getTakenAtTimestamp() * 1000L));
        media.getThumbnailResources().sort(Comparator.comparing(ThumbnailResource::getConfigWidth));
        media.getThumbnailResources().stream().findFirst().ifPresent(resource -> result.setThumbnailSrc(resource.getSrc()));
        return result;
    }

    private MainPage getMainPage(String html) {
        String json = StringHelper.getStringBetween(html, "window._sharedData =", ";</script>").trim();
        try {
            return jsonService.getJson(json, MainPage.class);
        } catch (JsonConvertingException e) {
            throw new ProfilePageEmptyException("Error on get main page, html:" + html, e);
        }
    }
}
