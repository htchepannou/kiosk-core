package com.tchepannou.kiosk.core.service;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class UrlServiceProviderTest {
    UrlServiceProvider provider = new UrlServiceProvider();

    @Test
    public void testGet() throws Exception {
        final UrlService service1 = mock(UrlService.class);
        final UrlService service2 = mock(UrlService.class);
        provider.register("http://", service1);
        provider.register("s3://", service2);

        assertThat(provider.get("http://www.google.com")).isEqualTo(service1);
        assertThat(provider.get("HTTP://www.google.com")).isEqualTo(service1);
        assertThat(provider.get("s3://foo/bar")).isEqualTo(service2);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionIfNotSupported() throws Exception {
        provider.get("/foo/bar");
    }
}
