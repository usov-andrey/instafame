/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.direct.service;

/**
 * Обновляет сообщения с каким-то пользователем из direct инстаграма
 *
 * @author Usov Andrey
 * @since 29/01/2020
 */
public class DirectThreadUpdater {
    /*

    private final InstagramDirectServiceImpl service;
    private final MessageService messageService;
    private final MessageThreadService messageThreadService;
    //private final DirectThread thread;
    private MessageThreadProvider messageThreadProvider;

    public DirectThreadUpdater(InstagramDirectServiceImpl service, MessageService messageService,
                               MessageThreadService messageThreadService, DirectThread thread) {
        this.service = service;
        this.messageService = messageService;
        this.messageThreadService = messageThreadService;
        this.thread = thread;
    }

    public void update() throws ServiceTemporaryException {
        User user = thread.getUsers().get(0);
        messageThreadProvider = service.messageThreadProvider(user);
        Set<String> messageItemIds = messageThreadProvider.getMessageItemIds(messageService, MessageChannel.instagram);
        if (needToUpdateThread(messageItemIds)) {
            service.updateThreadMessages(thread, items -> updateThreadItems(items, user, messageItemIds));
        }
    }

    private void updateThreadItems(List<Item> items, User user, Set<String> messageItemIds) {
        items.sort(Comparator.comparing(Item::getTimestamp));
        //Уже сохранянные сообщения
        for (Item item : items) {
            String itemId = item.getItemId();
            if (messageItemIds.add(itemId) && !item.getItemType().equalsIgnoreCase("action_log")) {
                MessageBean bean = new MessageBean();
                bean.setInternalId(itemId);
                bean.setThread(messageThreadProvider.getOrCreateBean(messageThreadService));
                bean.setChannel(MessageChannel.instagram);
                bean.setCreateTime(new Date(item.getTimestamp() / 1000));
                bean.setMessageType(item.getUserId().equals(user.getPk()) ? MessageType.inbound : MessageType.outbound);
                bean.setContactData(user.getUsername());
                String text = getItemText(item);
                bean.setText(text);
                messageService.save(bean);
            }
        }
    }

    private String getItemText(Item item) {
        String text;
        if (item.getItemType().equals("link")) text = item.getLink().getText();
        else text = item.getItemType().equals("voice_media") ? "Голосовое сообщение" :
                item.getText();
        if (StringHelper.isEmpty(text)) {
            text = item.getItemType();
        }
        return text;
    }

    private boolean needToUpdateThread(Set<String> messageItemIds) {
        LastPermanentItem lastPermanentItem = thread.getLastPermanentItem();
        if (lastPermanentItem != null) {
            return !messageItemIds.contains(lastPermanentItem.getItemId());
        }
        if (!thread.getItems().isEmpty()) {
            Item item = thread.getItems().get(0);
            return !messageItemIds.contains(item.getItemId());
        }
        return false;
    }
*/
}
