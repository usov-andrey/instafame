/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25;

import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.json.JsonService;
import com.xalap.instagram.api.InstagramAccount;
import com.xalap.instagram.mgp25.media.MediaResponse;
import com.xalap.instagram.mgp25.user.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * @author Усов Андрей
 * @since 31/05/2019
 */
public class MGP25Executor {

    private static final Logger log = LoggerFactory.getLogger(MGP25Executor.class);
    private final JsonService jsonService;
    private final PhpExecutor executor;
    private final InstagramAccount account;

    public MGP25Executor(JsonService jsonService, PhpExecutor executor, InstagramAccount loginPassword) {
        this.jsonService = jsonService;
        this.executor = executor;
        this.account = loginPassword;
    }

    private void checkForTempErrors(String s) {
        if (s.contains("Throttled by Instagram because of too many API requests") ||
                s.contains("Challenge required.") || s.contains("Feedback required")) {
            account.block();
            log.error(account + " is blocked. Bad response:" + s);
        }
        if (s.contains("Something went wrong")) {
            throw new SomethingWrongException(s);
        }
    }

    public void processUserLastMedia(String userName, Consumer<MediaResponse> mediaResponseConsumer) throws ServiceTemporaryException {
        exec(s -> processMedia(s, mediaResponseConsumer), "listLastMedia", userName);
    }

    public void processUserAllMedia(String userName, Consumer<MediaResponse> mediaResponseConsumer) throws ServiceTemporaryException {
        exec(s -> {
            processMedia(s, mediaResponseConsumer);
        }, "listMedia", userName);
    }

    private void processMedia(String s, Consumer<MediaResponse> mediaResponseConsumer) {
        checkForTempErrors(s);
        MediaResponse mediaResponse = jsonService.getJson(s, MediaResponse.class);
        mediaResponseConsumer.accept(mediaResponse);
    }

    /**
     * Обрабатывает последние media
     * @deprecated use processUserLastMedia
     */
    public void processUserLastMediaCodes(String userName, Consumer<String> mediaCodeConsumer) throws ServiceTemporaryException {
        exec(mediaCodeConsumer, "listLastMediaCodes", userName);
    }

    /**
     * Обрабатывает все media
     * @deprecated use processLastMedia
     */
    public void processUserAllMediaCodes(String userName, Consumer<String> mediaCodeConsumer) throws ServiceTemporaryException {
        exec(mediaCodeConsumer, "listMediaCodes", userName);
    }

    public void processSuggestedUsers(String userName, Consumer<String> userNameConsumer) throws ServiceTemporaryException {
        exec(userNameConsumer, "listUserSuggestions", userName);
    }

    public void processUserFollowers(String userName, Consumer<String> followerUserNameConsumer) throws ServiceTemporaryException {
        exec(followerUserNameConsumer, "listUserFollowers", userName);
    }

    /**
     * @param maxCount - максимальное количество подписчиков для считывания
     */
    public void processUserFollowers(String userName, int maxCount, Consumer<String> followerUserNameConsumer) throws ServiceTemporaryException {
        exec(followerUserNameConsumer, "listLastUserFollowers", userName, Integer.toString(maxCount));
    }

    /**
     * Делаем запрос к php
     * Одновременнно можно только один запрос делать
     */
    private void exec(Consumer<String> lineConsumer, String fileName, String... args) throws ServiceTemporaryException {
        if (account.isBlocked()) {
            throw new BlockedException("Account is blocked:" + account);
        }
        List<String> params = new ArrayList<>();
        params.add(account.getLogin());
        params.add(account.getPassword());
        List<String> argList = Arrays.asList(args);
        params.addAll(argList);
        try {
            executor.exec(lineConsumer, fileName, params);
        } catch (ServiceTemporaryException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Error with account:" + account + " message:" + e.getMessage(), e);
        }
    }

    public void processDirectMessages(String lastActivityAt, Consumer<String> consumer) throws ServiceTemporaryException {
        exec(consumer, "listDirectMessages", lastActivityAt);
    }

    public void processDirectPendingMessages(Consumer<String> consumer) throws ServiceTemporaryException {
        exec(consumer, "listDirectPending");
    }

    public void approveAllPendingThreads(Consumer<String> consumer) throws ServiceTemporaryException {
        exec(consumer, "approvePendingThreads");
    }

    public void processDirectThread(String threadId, Consumer<String> consumer) throws ServiceTemporaryException {
        exec(consumer, "listDirectThread", threadId);
    }

    public void sendMessage(String userId, String text, Consumer<String> consumer) throws ServiceTemporaryException {
        exec(consumer, "sendDirectMessage", userId, text);
    }

    public void getThreadByUser(String userId, Consumer<String> consumer) throws ServiceTemporaryException {
        exec(consumer, "getDirectThreadByUser", userId);
    }

    public UserResponse getUser(String userName) throws ServiceTemporaryException {
        AtomicReference<UserResponse> responseAtomicReference = new AtomicReference<>();
        try {
            exec(s -> {
                if (s.contains("User not found") || s.contains("Requested resource does not exist")) {
                    return;
                }
                checkForTempErrors(s);
                UserResponse userResponse = jsonService.getJson(s, UserResponse.class);
                responseAtomicReference.set(userResponse);
            }, "getUser", userName);
        } catch (SomethingWrongException e) {
            throw new ServiceTemporaryException("Something went wrong Exception", e);
        } catch (ServiceTemporaryException e) {
            throw e;
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("User not found")) {
                return null;
            }
            throw new IllegalStateException("Error on getUser:" + userName, e);
        }
        return responseAtomicReference.get();
    }


}
