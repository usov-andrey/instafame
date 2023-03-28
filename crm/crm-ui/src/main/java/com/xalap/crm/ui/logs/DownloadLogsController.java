/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.logs;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.HtmlUtils;

import java.io.File;

/**
 * Скачивание логов
 *
 * @author Usov Andrey
 * @since 2020-03-27
 */
@Controller
public class DownloadLogsController {

    public static final String MAPPING = "/logs/";
    public static final String DOWNLOAD = MAPPING + "download/";
    private static final String INTERNAL = MAPPING + "internal/";

    private final DownloadLogsService downloadLogsService;

    public DownloadLogsController(DownloadLogsService downloadLogsService) {
        this.downloadLogsService = downloadLogsService;
    }

    @GetMapping(value = INTERNAL)
    public ResponseEntity<FileSystemResource> getFile(String fileName) {
        File logsFile = downloadLogsService.getLogsFile(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment; filename=\"logs.zip\"").build());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(logsFile.length());

        return ResponseEntity.ok().headers(headers).body(new FileSystemResource(logsFile));
    }

    @GetMapping(value = DOWNLOAD + "{appName}")
    public RedirectView download(@PathVariable String appName, int hours) {
        String fileName = downloadLogsService.createTempLogsFile(HtmlUtils.htmlEscape(appName), hours);
        return new RedirectView(INTERNAL + "?fileName=" + fileName);
    }

}
