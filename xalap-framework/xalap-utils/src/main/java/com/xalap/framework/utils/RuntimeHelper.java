/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Выполнение программы
 *
 * @author Усов Андрей
 * @since 07.06.2018
 */
public class RuntimeHelper {

    private static final Logger log = LoggerFactory.getLogger(RuntimeHelper.class);

    public static int execute(String[] cmd, Consumer<String> outConsumer, File workDir) throws Exception {
        StringBuilder err = new StringBuilder();
        Runtime runtime = Runtime.getRuntime();
        log.debug("Exec:" + Arrays.asList(cmd) + " in workDir:" + workDir);
        Process proc = runtime.exec(cmd, null, workDir);

        InputStream inputStream = proc.getInputStream();
        InputStream errorStream = proc.getErrorStream();

        Streamer err_stream = new Streamer(errorStream, IOHelper.stringBuilderConsumer(err));
        Streamer out_stream = new Streamer(inputStream, outConsumer);


            out_stream.run();
            int exitCode = proc.waitFor();

            err_stream.run();
            if (err.length() > 0) {
                log.error("Error on exec:" + Arrays.asList(cmd) + " exitCode=" + exitCode + " err:" + err.toString());
            }
            if (inputStream != null) inputStream.close();
            if (errorStream != null) errorStream.close();
            return exitCode;
    }

    static class Streamer extends Thread {

        private final InputStream input;
        private final Consumer<String> lineConsumer;

        Streamer(InputStream input, Consumer<String> lineConsumer) {
            this.input = input;
            this.lineConsumer = lineConsumer;
        }

        public void run() {
            IOHelper.readString(input, lineConsumer);
        }

    }


}
