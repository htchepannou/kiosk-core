package com.tchepannou.kiosk.core.text;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TextKitProviderTest {
    private TextKitProvider provider = new TextKitProvider();

    @Test
    public void testGetFR() throws Exception {
        assertThat(provider.get("fr")).isNotNull();
    }

    @Test(expected = IllegalStateException.class)
    public void testGetEN() throws Exception {
        provider.get("en");
    }

    @Test(expected = IllegalStateException.class)
    public void testGetNull() throws Exception {
        provider.get(null);
    }
}
