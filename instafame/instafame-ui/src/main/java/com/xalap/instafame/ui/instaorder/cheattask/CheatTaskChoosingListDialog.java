/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.cheattask;

import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.CheatTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.vaadin.custom.dialog.EntityChoosingListDialog;

/**
 * @author Усов Андрей
 * @since 10/07/2019
 */
public class CheatTaskChoosingListDialog extends EntityChoosingListDialog<CheatTaskBean> {

    public CheatTaskChoosingListDialog(CheatTaskService cheatTaskService, InstaOrderTaskType taskType) {
        super(aClass -> new CheatTaskGridPanel());
        gridPanel.dataSource().setMemoryDataProvider(() -> cheatTaskService.getEnabledSortedTasks(taskType));
    }
}
