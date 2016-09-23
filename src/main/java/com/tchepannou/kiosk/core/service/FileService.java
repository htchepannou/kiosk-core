package com.tchepannou.kiosk.core.service;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileService implements UrlService {
    private final File home;

    public FileService(final File directory) {
        this.home = directory;
    }

    @Override
    public void put(final String key, final InputStream content) throws IOException {
        final File file = toFile(key);
        final File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (OutputStream out = new FileOutputStream(file)) {
            IOUtils.copy(content, out);
        }
    }

    @Override
    public void get(final String key, final OutputStream out) throws IOException {
        final File file = toFile(key);
        try (InputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        }
    }

    private File toFile (final String key){
        return new File(home.getAbsolutePath() + "/" + key);
    }
}
