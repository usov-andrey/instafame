/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.media;

import com.xalap.framework.data.CrudService;
import com.xalap.framework.logging.HasLog;
import com.xalap.instagram.api.media.Media;
import com.xalap.instagram.service.tag.TagService;
import com.xalap.instagram.service.user.InstagramUserBean;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Service
public class MediaService extends CrudService<MediaBean, MediaRepository, Integer> implements HasLog {

    private final MediaRepository mediaRepository;
    private final MediaHistoryRepository mediaHistoryRepository;
    private final TagService tagService;

    public MediaService(MediaRepository mediaRepository, MediaHistoryRepository mediaHistoryRepository, TagService tagService) {
        this.mediaRepository = mediaRepository;
        this.mediaHistoryRepository = mediaHistoryRepository;
        this.tagService = tagService;
    }

    private MediaBean getMediaByInternalId(String internalId) {
        return getFirstAndDeleteAnothers(mediaRepository.findByInternalId(internalId));
    }

    private MediaBean getFirstAndDeleteAnothers(List<MediaBean> mediaBeanList) {
        if (mediaBeanList.isEmpty()) {
            return null;
        }
        if (mediaBeanList.size() > 1) {
            for (int i = 1; i < mediaBeanList.size(); i++) {
                delete(mediaBeanList.get(i));
            }
        }
        return mediaBeanList.get(0);
    }

    /**
     * Общий алгоритм обновление информации о media
     */
    public MediaBean updateMedia(Media media, String userName, MediaListener mediaListener) {
        MediaBean mediaBean = getMediaByInternalId(media.getId());
        boolean isNew = mediaBean == null;
        boolean commentsCountChanged = false;
        boolean likesCountChanged = false;
        boolean viewVideoCountChanged = false;
        if (isNew) {
            mediaBean = new MediaBean();
            mediaBean.create(media, userName);
            mediaListener.setUser(mediaBean);
            commentsCountChanged = media.getCommentsCount() > 0;
            likesCountChanged = media.getLikesCount() > 0;
            viewVideoCountChanged = media.getViewViewCount() != null && media.getViewViewCount() > 0;
        } else {
            if (mediaBean.getStats().isChanged(media)) {
                MediaHistoryBean historyBean = new MediaHistoryBean();
                historyBean.setCreateTime(new Date());
                historyBean.update(media);
                historyBean.setMedia(mediaBean);
                mediaHistoryRepository.save(historyBean);
                commentsCountChanged = mediaBean.getStats().getCommentsCount() != media.getCommentsCount();
                likesCountChanged = mediaBean.getStats().getLikesCount() != media.getLikesCount();
            }
        }
        mediaListener.setUser(mediaBean);
        mediaBean.update(media);
        updateMediaHashTags(mediaBean, media);
        mediaBean = save(mediaBean);

        if (commentsCountChanged) {
            mediaListener.newComments(mediaBean);
        }
        if (likesCountChanged) {
            mediaListener.newLikes(mediaBean);
        }
        if (isNew) {
            mediaListener.newMedia(mediaBean);
        }
        return mediaBean;
    }

    /*
    public MediaBean updateMedia(String mediaCode, MediaListener mediaListener) {
        MediaWithLastActivity mediaWithLastActivity = mediaWithLastActivity(mediaCode);
        if (mediaWithLastActivity == null) {
            return null;
        }
        return updateMedia(mediaWithLastActivity, mediaListener);
    }*/

    /**
     * Получаем теги из caption и комментариев и сохраняем их в базе
     */
    private void updateMediaHashTags(MediaBean mediaBean, Media media) {
        Set<String> hashTagNames = new HashSet<>();
        if (mediaBean.getHashTags() != null) {
            tagService.extractTags(hashTagNames, mediaBean.getHashTags());
        }

        Set<String> newHashTagNames = hashTagNames(media, mediaBean.getOwner());
        if (hashTagNames.addAll(newHashTagNames)) {
            mediaBean.setHashTags(tagService.formatTags(newHashTagNames));
            save(mediaBean);
        }
    }

    private Set<String> hashTagNames(Media media, String owner) {
        Set<String> hashTagNames = new HashSet<>();
        tagService.extractTags(hashTagNames, media.getCaption());
        return hashTagNames;
    }

    public List<MediaBean> getUserMediaList(InstagramUserBean userBean) {
        return mediaRepository.findByUser(userBean);
    }

    /**
     * @return Последние count публикаций у пользователя userBean
     */
    public List<MediaBean> getUserLastMediaList(InstagramUserBean userBean, int count) {
        List<MediaBean> userMediaList = getUserMediaList(userBean);
        MediaBean.sortByDate(userMediaList);
        return userMediaList.stream().filter(mediaBean -> !Boolean.TRUE.equals(mediaBean.getDeleted())).limit(count).collect(Collectors.toList());
    }

    public List<MediaBean> getUserLastMediaList(InstagramUserBean userBean) {
        List<MediaBean> userMediaList = getUserMediaList(userBean);
        MediaBean.sortByDate(userMediaList);
        return userMediaList.stream().filter(mediaBean -> !Boolean.TRUE.equals(mediaBean.getDeleted())).collect(Collectors.toList());
    }

    private int maxER(Collection<MediaBean> mediaBeanCollection) {
        int maxEr = 0;
        for (MediaBean mediaBean : mediaBeanCollection) {
            if (mediaBean.getStats().er() > maxEr) {
                maxEr = mediaBean.getStats().er();
            }
        }
        return maxEr;
    }

    /**
     * @return Предыдущие count публикаций
     */
    private List<MediaBean> getPreviousMedia(List<MediaBean> userMediaList, MediaBean mediaBean, int count) {
        Collections.sort(userMediaList, (o1, o2) -> o1.getCreateTime().compareTo(o2.getCreateTime()));
        List<MediaBean> result = new ArrayList<>();
        int counter = 0;
        for (MediaBean bean : userMediaList) {
            if (bean.equals(mediaBean)) {
                counter = 1;
            } else if (counter > 0) {
                result.add(bean);
                if (counter == count) {
                    break;
                }
                counter++;
            }
        }
        return result;
    }

    public Set<String> hashTagNameSet(List<MediaBean> mediaBeanList) {
        Set<String> hashTagNameSet = new HashSet<>();
        for (MediaBean mediaBean : mediaBeanList) {
            if (mediaBean.getHashTags() != null) {
                hashTagNameSet.addAll(tagService.extractTags(mediaBean.getHashTags()));
            }
        }
        return hashTagNameSet;
    }

    /**
     * Удалить неиспользуемые media
     */
    public void deleteMedias(InstagramUserBean user, Set<String> mediaIdSet, Date minCreateTime, Date maxCreateTime) {
        List<MediaBean> mediaBeanList = getRepository().findByUserAndCreateTimeBetween(user, minCreateTime, maxCreateTime);
        for (MediaBean mediaBean : mediaBeanList) {
            if (!mediaIdSet.contains(mediaBean.getInternalId())) {
                mediaBean.setDeleted(true);
                save(mediaBean);
            }
        }
    }
}
