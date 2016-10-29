package com.tchepannou.kiosk.core.text.impl;

import com.tchepannou.kiosk.core.text.Tokenizer;
import org.apache.commons.lang3.StringUtils;

public class UnaccentTokenizer implements Tokenizer {
    private final Tokenizer delegate;

    public UnaccentTokenizer(final Tokenizer delegate) {
        this.delegate = delegate;
    }

    @Override
    public String nextToken() {
        final String token = delegate.nextToken();
        return token != null ? StringUtils.stripAccents(token) : null;
    }

}
