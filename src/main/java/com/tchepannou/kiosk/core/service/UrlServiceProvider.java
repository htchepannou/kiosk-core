package com.tchepannou.kiosk.core.service;

import java.util.HashMap;
import java.util.Map;

public class UrlServiceProvider {
    private final Map<String, UrlService> services = new HashMap<>();

    public void register(final String prefix, final UrlService service) {
        services.put(prefix.toLowerCase(), service);
    }

    public UrlService get(final String url) {
        final String xurl = url.toLowerCase();
        for (final String prefix : services.keySet()) {
            if (xurl.startsWith(prefix)) {
                return services.get(prefix);
            }
        }

        throw new IllegalStateException("No UrlService available for " + url);
    }
}
