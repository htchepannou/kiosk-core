package com.tchepannou.kiosk.core.text.impl;

import com.tchepannou.kiosk.core.text.Tokenizer;

public class LowerCaseTokeniner implements Tokenizer {
    private final Tokenizer delegate;

    public LowerCaseTokeniner(final Tokenizer delegate) {
        this.delegate = delegate;
    }

    @Override
    public String nextToken() {
        final String token =  delegate.nextToken();
        return token != null ? token.toLowerCase() : null;
    }
}
