package com.tchepannou.kiosk.core.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface UrlService {
    void get(final String url, final OutputStream out) throws IOException;
    void put(final String url, final InputStream content) throws IOException;

}
