/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.dialog;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

import java.util.function.Consumer;

/**
 * @author Usov Andrey
 * @since 2020-08-27
 */
public class FileUploadDialog {

    private final Consumer<MemoryBuffer> bufferConsumer;

    private FileUploadDialog(Consumer<MemoryBuffer> bufferConsumer) {
        this.bufferConsumer = bufferConsumer;
    }

    public static void open(Consumer<MemoryBuffer> bufferConsumer) {
        new FileUploadDialog(bufferConsumer).open();
    }

    public void open() {
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        Dialog dialog = new Dialog();

        upload.addSucceededListener(succeededEvent -> {
            bufferConsumer.accept(buffer);
            dialog.close();
        });

        dialog.add(upload);

        dialog.open();
    }
}
