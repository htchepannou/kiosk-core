package com.tchepannou.kiosk.core.service;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpService implements UrlService {
    private static final String VERSION = "1.0";
    private static final String USER_AGENT = String.format("Mozilla/5.0 (compatible; Kioskbot/%s)", VERSION);

    static {
        System.setProperty("http.agent", USER_AGENT);
    }

    private final int connectionTimeout;

    public HttpService() {
        this(1500);
    }

    public HttpService(final int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void get(final String url, final OutputStream out) throws IOException {
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpGet method = new HttpGet(url);
        method.setHeader("Connection", "keep-alive");
        method.setHeader("User-Agent", USER_AGENT);
        try (CloseableHttpResponse response = client.execute(method)) {
            final InputStream in = getContentAsUTF8(response);
            IOUtils.copy(in, out);
        }
    }

    @Override
    public void put(final String url, final InputStream content) throws IOException {
        throw new IllegalStateException("Not supported exception");
    }

    private InputStream getContentAsUTF8(final CloseableHttpResponse response) throws IOException {
        final InputStream in = response.getEntity().getContent();
        final String encoding = getEncoding(response);
        if (encoding == null || "utf-8".equalsIgnoreCase(encoding)) {
            return in;
        }

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(in, out);
        final String html = new String(out.toByteArray(), encoding);
        final byte[] utf8 = html.getBytes("utf-8");
        return new ByteArrayInputStream(utf8);
    }

    private String getEncoding(final CloseableHttpResponse response) {
        final Header contentType = response.getFirstHeader("Content-Type");
        if (contentType != null) {
            final String value = contentType.getValue();
            final String charset = "charset=";
            final int i = value.indexOf(charset);
            if (i > 0) {
                return value.substring(i + charset.length());
            }
        }
        return null;
    }
}
