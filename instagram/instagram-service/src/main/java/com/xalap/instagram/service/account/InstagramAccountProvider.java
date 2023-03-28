/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.account;

import com.xalap.framework.utils.CollectionHelper;
import com.xalap.instagram.api.InstagramAccount;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Хранилище аккаунтов с которыми идет работа с инстаграм
 *
 * @author Usov Andrey
 * @since 2020-08-29
 */
@Service
public class InstagramAccountProvider {

    private final List<InstagramAccount> accountsForRead = new ArrayList<>();
    private final Iterator<InstagramAccount> accountIterator = CollectionHelper.repeatIterator(accountsForRead);

    public InstagramAccountProvider() {
        accountsForRead.add(new InstagramAccount("jasmine.live.th", "B@v(8Tr"));
        //accountsForRead.add(new InstagramAccount("insta.boss.ru", "1Qaz2Wsx3Edc"));
        //"veronika.grom18@gmail.com", "Z62a!2A"
    }

    public List<InstagramAccount> getAccountsForRead() {
        return accountsForRead;
    }

    /**
     * @return true, если все аккаунты заблокированы
     */
    public boolean isBlocked() {
        for (InstagramAccount account : accountsForRead) {
            if (!account.isBlocked()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return Аккаунт для основных операций по чтению информации об других аккаунтов, их медиа, подписчиков
     */
    public InstagramAccount getForReadOperations() {
        int index = 0;
        Iterator<InstagramAccount> accountIterator = this.accountIterator;
        while (accountIterator.hasNext()) {
            InstagramAccount account = accountIterator.next();
            if (!account.isBlocked()) {
                return account;
            }
            index++;
            if (index > accountsForRead.size()) {
                return account;
            }
        }
        return accountsForRead.get(0);
    }

}
