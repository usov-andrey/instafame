/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25;

import com.xalap.framework.thread.ThreadService;
import com.xalap.framework.utils.RuntimeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Реализация этого класса не подразумевает, что будет запущено больше одного экземпляра приложения
 * Так как на методе есть synchronized.
 * Если нужно в кластере, зачем-то, то нужно подключать LockService
 * @author Усов Андрей
 * @since 26/06/2019
 */
public class PhpExecutor {

    private static final Logger log = LoggerFactory.getLogger(PhpExecutor.class);

    @Autowired
    private ThreadService threadService;

    private final String path;

    public PhpExecutor(String path) {
        this.path = path;
    }

    protected String getPhp() {
        return "php";
    }

    /**
     * В случае ошибки компиляции, возвращается вначале пустая строка, а потом строка с ошибкой
     */
    public synchronized void exec(Consumer<String> lineConsumer, String fileName, List<String> args) throws Exception {
        execInternal(lineConsumer, fileName, args);
    }

    private void execInternal(Consumer<String> lineConsumer, String fileName, List<String> args) throws Exception {
        List<String> params = new ArrayList<>();
        params.add(getPhp());
        params.add(fileName + ".php");
        params.addAll(args);

        File workDir = path != null ? new File(path) : null;
        if (workDir != null && !workDir.exists()) {
            throw new IllegalStateException("WorkDir is not exists:" + path);
        }
        //Ждем случайное время между запросами
        int randomSeconds = (int) (10 * Math.random() + 1);
        threadService.sleep(randomSeconds * 1000);

        RuntimeHelper.execute(params.toArray(new String[0]), s -> {
            //Из-за того, что и при ошибке вывод осуществляется в out лог, выводим все это в консоль
            log.debug(fileName + ":" + s);
            //В случае ошибок компиляции возвращается вначале пустая строка, а уже потом строка с сообщением об ошибке
            //Поэтому убираем пустые строки из результата
            if (!s.isEmpty()) {
                lineConsumer.accept(s);
            }
        }, workDir);
    }


}
