/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.logs;

import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.LogsHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

/**
 * @author Usov Andrey
 * @since 2020-03-27
 */
@Service
public class DownloadLogsService {

    /**
     * Создаем временный zip архив с логами во временной папке
     * Возвращем имя файла
     */
    public String createTempLogsFile(String appName, int hours) {
        File tmpDirFile = getTmpDirFile();
        byte[] bytes = LogsHelper.getLogsZip(Paths.get("C:\\Server\\logs"), appName,
                DateHelper.incHours(new Date(), -1 * hours));
        String fileName = "logs-" + UUID.randomUUID().toString();
        try {
            File file = File.createTempFile(fileName, ".zip", tmpDirFile);
            file.deleteOnExit();
            Files.write(file.toPath(), bytes);
            return file.getName();
        } catch (IOException e) {
            throw new IllegalStateException("Error on create temp file:" + fileName, e);
        }
    }

    private File getTmpDirFile() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    File getLogsFile(String fileName) {
        return new File(getTmpDirFile(), fileName);
    }
}
