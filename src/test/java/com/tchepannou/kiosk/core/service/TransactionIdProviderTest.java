package com.tchepannou.kiosk.core.service;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionIdProviderTest {
    TransactionIdProvider provider = new TransactionIdProvider();

    @Test
    public void testGet() throws Exception {
        assertThat(provider.get()).isNotNull();
    }
}
