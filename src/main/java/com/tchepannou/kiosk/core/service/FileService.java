package com.tchepannou.kiosk.core.service;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileService implements UrlService {
    protected final String S3_PREFIX = "s3://";
    protected final String home;

    protected FileService(final String home){
        this.home = home;
    }
    public FileService(final File home) {
        this(home.getAbsolutePath());
    }

    @Override
    public void put(final String url, final InputStream content) throws IOException {
        final String key = url.startsWith(S3_PREFIX) ? url.substring(S3_PREFIX.length()) : url;

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
    public void get(final String url, final OutputStream out) throws IOException {
        final String key = url.startsWith(S3_PREFIX) ? url.substring(S3_PREFIX.length()) : url;

        final File file = toFile(key);
        try (InputStream in = new FileInputStream(file)) {
            IOUtils.copy(in, out);
        }
    }

    private File toFile (final String key){
        return new File(home + "/" + key);
    }
}
