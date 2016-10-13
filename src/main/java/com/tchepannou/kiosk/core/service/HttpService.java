package com.tchepannou.kiosk.core.service;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.txt.CharsetDetector;
import org.apache.tika.parser.txt.CharsetMatch;

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
        try (final CloseableHttpClient client = HttpClients.createDefault()) {
            final HttpGet method = createHttpGet(url);
            try (CloseableHttpResponse response = client.execute(method)) {
                final InputStream in = isText(response)
                        ? getContentTextAsUTF8(response)
                        : response.getEntity().getContent();
                IOUtils.copy(in, out);
            }
        }
    }

    public String get(final String url, final String keyPrefix, final FileService fileService) throws IOException {
        try (final CloseableHttpClient client = HttpClients.createDefault()) {
            final HttpGet method = createHttpGet(url);
            try (CloseableHttpResponse response = client.execute(method)) {
                final InputStream in = isText(response)
                        ? getContentTextAsUTF8(response)
                        : response.getEntity().getContent();
                final String key = keyPrefix + extension(response);
                fileService.put(key, in);
                return key;
            }
        } catch (final MimeTypeException e) {
            throw new IOException("Unable to resolve mime-type", e);
        }
    }

    private String extension(final CloseableHttpResponse response) throws MimeTypeException {
        final Header header = response.getFirstHeader("Content-Type");
        if (header != null) {
            final String value = header.getValue();
            final MimeType mime = MimeTypes.getDefaultMimeTypes().forName(value);
            return mime.getExtension();
        }
        return "";
    }

    @Override
    public void put(final String url, final InputStream content) throws IOException {
        throw new IllegalStateException("Not supported exception");
    }

    private HttpGet createHttpGet(final String url) {
        final HttpGet method = new HttpGet(url);
        method.setHeader("Connection", "keep-alive");
        method.setHeader("User-Agent", USER_AGENT);
        return method;
    }

    private InputStream getContentTextAsUTF8(final CloseableHttpResponse response) throws IOException {
        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
        IOUtils.copy(response.getEntity().getContent(), bout);

        final byte[] bytes = bout.toByteArray();
        final CharsetDetector detector = new CharsetDetector();
        detector.setText(bytes);

        final CharsetMatch charset = detector.detect();
        if (charset == null || "utf-8".equalsIgnoreCase(charset.getName())) {
            return new ByteArrayInputStream(bout.toByteArray());
        }

        final String encoding = charset.getName();
        final String html = new String(bytes, encoding);
        final byte[] utf8 = html.getBytes("UTF-8");
        return new ByteArrayInputStream(utf8);
    }

    private boolean isText(final CloseableHttpResponse response) {
        final Header contentType = response.getFirstHeader("Content-Type");
        return contentType != null && contentType.getValue().startsWith("text/");
    }
}
