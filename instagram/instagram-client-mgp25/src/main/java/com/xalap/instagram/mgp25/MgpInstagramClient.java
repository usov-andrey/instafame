/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25;

import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.json.JsonService;
import com.xalap.instagram.api.InstagramAccount;
import com.xalap.instagram.api.InstagramUserApi;
import com.xalap.instagram.api.media.Media;
import com.xalap.instagram.api.media.MediaType;
import com.xalap.instagram.api.user.User;
import com.xalap.instagram.mgp25.media.Candidate;
import com.xalap.instagram.mgp25.media.CarouselMedium;
import com.xalap.instagram.mgp25.media.ImageVersions2;
import com.xalap.instagram.mgp25.media.MediaResponse;
import com.xalap.instagram.mgp25.user.UserResponse;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Работа с инстаграм через mgp25
 *
 * @author Usov Andrey
 * @since 2020-08-29
 */
@Service
public class MgpInstagramClient implements InstagramUserApi {

    private final PhpExecutor phpExecutor;
    private final JsonService jsonService;

    public MgpInstagramClient(PhpExecutor phpExecutor, JsonService jsonService) {
        this.phpExecutor = phpExecutor;
        this.jsonService = jsonService;
    }

    @Override
    public List<Media> getLastMediaList(InstagramAccount account, String userName) throws ServiceTemporaryException {
        List<Media> result = new ArrayList<>();
            getMgp25ExecutorForRead(account).processUserLastMedia(userName,
                    mediaResponse -> result.add(createMedia(mediaResponse)));
        return result;
    }

    @Override
    public List<Media> getAllMediaList(InstagramAccount account, String userName) throws ServiceTemporaryException {
        List<Media> result = new ArrayList<>();
        getMgp25ExecutorForRead(account).processUserAllMedia(userName,
                mediaResponse -> result.add(createMedia(mediaResponse)));
        return result;
    }

    @Override
    public Set<String> getFollowers(InstagramAccount account, String userName) throws ServiceTemporaryException {
        Set<String> followers = new HashSet<>();
        getMgp25ExecutorForRead(account).processUserFollowers(userName, followers::add);
        return followers;
    }

    @Override
    public Set<String> getLastFollowers(InstagramAccount account, String userName, int followersCount) throws ServiceTemporaryException {
        Set<String> followers = new HashSet<>();
        getMgp25ExecutorForRead(account).processUserFollowers(userName, followersCount, followers::add);
        return followers;
    }

    @Override
    public Optional<User> readUser(InstagramAccount account, String userName) throws ServiceTemporaryException {
        UserResponse user = getMgp25ExecutorForRead(account).getUser(userName);
        if (user != null) {
            User user1 = new User();
            user1.setId(user.getPk());
            user1.setUserName(user.getUsername());
            user1.setBiography(user.getBiography());
            user1.setFullName(user.getFullName());
            user1.setFollowsCount(user.getFollowingCount());
            user1.setFollowedByCount(user.getFollowerCount());
            user1.setMediaCount(user.getMediaCount());
            user1.setPrivate(user.getIsPrivate());
            user1.setExternalLink(user.getExternalUrl());
            user1.setProfilePicture(user.getProfilePicUrl());
            return Optional.of(user1);
        } else {
            return Optional.empty();
        }
    }

    private MGP25Executor getMgp25ExecutorForRead(InstagramAccount account) {
        return new MGP25Executor(jsonService, phpExecutor, account);
    }

    /**
     * @return null, если thread не создан
     */
    /*
    public DirectThread getThreadByUser(InstagramAccount account, String userInternalId) throws ServiceTemporaryException {
        AtomicReference<DirectThread> result = new AtomicReference<>();
        new MGP25Executor(jsonService, phpExecutor, account).getThreadByUser(userInternalId, s -> {
            DirectThreadResponse response = getResult(s, DirectThreadResponse.class);
            result.set(response.getThread());
        });
        return result.get();
    }

    public void updateAllPendingThreads() throws ServiceTemporaryException {
        new MGP25Executor(converterService, phpExecutor, account()).approveAllPendingThreads(s -> {
            StatusResponse response = getResult(s, StatusResponse.class);
            if (!"ok".equals(response.getStatus())) {
                throw new IllegalStateException("Error on update pending threads:" + s);
            }
        });
    }

    private void updateThreads(String lastActivityAt, Consumer<DirectThread> threadConsumer) throws ServiceTemporaryException {
        new MGP25Executor(converterService, phpExecutor, account()).processDirectMessages(lastActivityAt, s -> {
            DirectInboxResponse response = getResult(s, DirectInboxResponse.class);
            response.getInbox().getThreads().forEach(threadConsumer);
        });
    }

    void updateThreadMessages(DirectThread thread, Consumer<List<Item>> messagesConsumer) throws ServiceTemporaryException {
        new MGP25Executor(converterService, phpExecutor, account()).processDirectThread(thread.getThreadId(), s -> {
            DirectThreadResponse response = getResult(s, DirectThreadResponse.class);
            messagesConsumer.accept(response.getThread().getItems());
        });
    }

    public void sendMessage(InstagramUserBean user, String text, Integer messageId) {
        AtomicReference<Payload> payloadAtomicReference = new AtomicReference<>();
        try {
            new MGP25Executor(converterService, phpExecutor, account()).sendMessage(user.getInternalId(), text, s -> {
                SendTextResponse response = getResult(s, SendTextResponse.class);
                payloadAtomicReference.set(response.getPayload());
            });
            notificationService.sendMessage("Отправлено сообщение к " + user.accountUrl() + ": " + text);
            Payload payload = payloadAtomicReference.get();
            MessageBean messageBean = messageService.get(messageId);
            Date time = new Date(Long.parseLong(payload.getTimestamp()) / 1000);
            messageBean.setCreateTime(time);
            messageBean.setInternalId(payload.getItemId());
            messageBean.setMessageType(MessageType.outbound);
            messageService.save(messageBean);
        } catch (Exception e) {
            messageService.errorSend(messageId);
            throw new IllegalStateException("Error on sendMessage for instagram:" + user.accountUrl(), e);
        }
    }*/

    private <T> T getResult(String s, Class<T> resultClass) {
        if (s.contains("Something went wrong")) {
            throw new SomethingWrongException(s);
        }
        return jsonService.getJson(s, resultClass);
    }

    private Media createMedia(MediaResponse media) {
        Media result = new Media();
        result.setId(media.getPk());
        result.setCode(media.getCode());

        result.setType(media.getMediaType() == 1 ? MediaType.image : media.getMediaType() == 8 ? MediaType.sideCar : MediaType.video);
        if (result.getType() == MediaType.sideCar) {
            CarouselMedium carouselMedium = media.getCarouselMedia().iterator().next();
            setThumbnail(result, carouselMedium.getImageVersions2());
        } else {
            setThumbnail(result, media.getImageVersions2());
        }
        result.setSrc(result.getThumbnailSrc());
        result.setCreateTime(new Date(Long.parseLong(media.getTakenAt()) * 1000L));
        if (media.getCaption() != null) {
            result.setCaption(media.getCaption().getText());
        } else {
            result.setCaption("");
        }
        if (media.getLikeCount() != null) {
            result.setLikesCount(media.getLikeCount());
        }
        if (media.getCommentCount() != null) {
            result.setCommentsCount(media.getCommentCount());
        }
        if (media.getViewCount() != null) {
            result.setViewViewCount(media.getViewCount());
        }
        return result;
    }

    private void setThumbnail(Media media, ImageVersions2 imageVersions2) {
        imageVersions2.getCandidates().sort(Comparator.comparing(Candidate::getWidth));
        imageVersions2.getCandidates().stream().findFirst().ifPresent((resource) -> {
            media.setThumbnailSrc(resource.getUrl());
        });
    }

}
