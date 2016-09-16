package com.tchepannou.kiosk.core.service;

import java.util.UUID;

public class TransactionIdProvider {
    private String transactionId;

    public String get () {
        if (transactionId == null){
            transactionId = UUID.randomUUID().toString();
        }
        return transactionId;
    }
}
