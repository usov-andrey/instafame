/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.configuration;

import com.xalap.instagram.mgp25.PhpExecutor;
import com.xalap.instagram.mgp25.WindowsPhpExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * При изменения на сервере в базе в очередь RabbitCrudNotifyMessage.QUEUE_NAME отправляются сообщения
 * с изменениями.
 * Локальный сервер, когда становится доступен, получает эти сообщения и добавляет данные локально
 * <p>
 * При попытке отправить сообщение в инстаграм на сервере, оно также отправляется в очередь
 * Локально же происходит попытка отправить сразу
 *
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Configuration
@Profile("!macos")
public class WindowsConfiguration {

    @Bean
    public PhpExecutor phpExecutor() {
        return new WindowsPhpExecutor();
    }

}
