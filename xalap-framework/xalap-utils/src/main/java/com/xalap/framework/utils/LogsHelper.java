/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Получение архива с логами системы
 *
 * @author Усов Андрей
 * @since 16/06/2019
 */
public class LogsHelper {

    protected static final Logger log = LoggerFactory.getLogger(LogsHelper.class);

    public static byte[] getLogsZip(String appName, Date fromTime) {
        String catalinaBase = WebHelper.getTomcatDir();
        Path logsPath = Paths.get(catalinaBase, "logs");
        return getLogsZip(logsPath, appName, fromTime);
    }

    public static byte[] getLogsZip(Path logsPath, String appName, Date fromTime) {
        try {
            Stream<Path> walk = Files.walk(logsPath);

            Set<Path> files = walk.filter(new Predicate<Path>() {
                @Override
                public boolean test(Path path) {
                    File file = path.toFile();
                    String fileName = file.getName();
                    if (fileName.endsWith(".log") &&
                            fileName.startsWith(appName)) {
                        //Файлы appName-out.log и appName-err.log добавляем в архив в любом случае
                        if (fileName.equals(appName + "-out.log") || fileName.equals(appName + "-err.log")) {
                            return true;
                        }
                        try {
                            BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
                            if (new Date(basicFileAttributes.lastModifiedTime().toMillis()).after(fromTime)) {
                                //log.debug("Add file to zip logs:" + file.getName());
                                return true;
                            }
                        } catch (IOException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                    return false;
                }
            }).collect(Collectors.toSet());
            log.debug("Zip " + files.size() + " for logs");
            return IOHelper.filesZip(files);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
