package com.tchepannou.kiosk.core.service;

import java.io.InputStream;
import java.io.OutputStream;

public interface ContentRepositoryService {
    void put(final String key, final InputStream content) throws ContentRepositoryException;
    void get(final String key, final OutputStream out) throws ContentRepositoryException;
}
