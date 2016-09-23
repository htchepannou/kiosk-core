package com.tchepannou.kiosk.core.service;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpService {
    private static final String VERSION = "1.0";
    private static final String USER_AGENT = String.format("Mozilla/5.0 (compatible; Kioskbot/%s)", VERSION);

    static {
        System.setProperty("http.agent", USER_AGENT);
    }

    private final int connectionTimeout;

    public HttpService(){
        this(1500);
    }
    public HttpService(int connectionTimeout){
        this.connectionTimeout = connectionTimeout;
    }

    public void get(final String url, final OutputStream out) throws IOException {
        final URL u = new URL(url);
        final HttpURLConnection hc = (HttpURLConnection) u.openConnection();
        try {
            hc.setDoOutput(true);
            hc.setDoInput(true);
            hc.setUseCaches(false);
            hc.setInstanceFollowRedirects(true);
            hc.setConnectTimeout(connectionTimeout * 1000);
            hc.setReadTimeout(connectionTimeout * 1000);
            hc.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            hc.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
            hc.setRequestProperty("Accept-Language", "en-US,en;q=0.8,es;q=0.6");
            hc.setRequestProperty("Connection", "keep-alive");

            try (final InputStream in = u.openStream()) {
                IOUtils.copy(in, out);
            }
        } finally {
            hc.disconnect();
        }
    }
}
