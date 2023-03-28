/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Функции по работе с IO
 *
 * @author Усов Андрей
 * @since 03.05.17
 */
public class IOHelper {

    private IOHelper() {
    }

    public static void writeString(File file, String contest) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            writeString(os, contest);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeString(OutputStream os, String data) {
        try {
            os.write(data.getBytes(StandardCharsets.UTF_8.displayName()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void writeData(File file, InputStream is) {
        try {
            Files.copy(
                    is,
                    file.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }    finally {
            closeQuietly(is);
        }
    }

    public static String read(Path path) {
        if (path.toFile().exists() ) {
            try {
                return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new IllegalStateException("Error on read file:"+path, e);
            }
        }
        return null;
    }

    public static void write(Path path, String text) {
        try {
            Files.write(path, text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IllegalStateException("Error on write file:"+path);
        }
    }

    /**
     * Чтение строки из потока ввода, поток закрыт не будет, управлять потоком в контексте вызова.
     *
     * @param in - поток ввода
     * @return строковое представление потока
     */
    public static String readString(InputStream in, Charset charset) {
        StringBuilder buf = new StringBuilder();
        readString(in, charset, stringBuilderConsumer(buf));
        return buf.toString();
    }

    public static void readString(InputStream in, Charset charset, Consumer<String> lineConsumer) {
        try {
            BufferedReader input = new BufferedReader(charset != null ? new InputStreamReader(in, charset) : new InputStreamReader(in));
            String line;
            while ((line = input.readLine()) != null) {
                lineConsumer.accept(line);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Чтение строки из потока ввода, поток закрыт не будет, управлять потоком в контексте вызова. Кодировка UTF-8
     */
    public static String readString(InputStream in) {
        return readString(in, StandardCharsets.UTF_8);
    }

    /**
     * Чтение строки из потока ввода, поток закрыт не будет, управлять потоком в контексте вызова. Кодировка UTF-8
     */
    public static void readString(InputStream in, Consumer<String> lineConsumer) {
        readString(in, StandardCharsets.UTF_8, lineConsumer);
    }

    public static Consumer<String> stringBuilderConsumer(StringBuilder buf) {
        String separator = System.getProperty("line.separator");
        return new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (buf.length() > 0) buf.append(separator);
                buf.append(s);
            }
        };
    }

    private static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    public static boolean createFile(File file) {
        try {
            return file.createNewFile();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static byte[] filesZip(Collection<Path> files) {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(bo);
        try {
            for (Path path : files) {
                File file = path.toFile();
                if (!file.isFile()) continue;
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOut.putNextEntry(zipEntry);
                zipOut.write(Files.readAllBytes(path));
                zipOut.closeEntry();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error on zip:" + files, e);
        } finally {
            closeQuietly(zipOut);
        }
        return bo.toByteArray();
    }
}
