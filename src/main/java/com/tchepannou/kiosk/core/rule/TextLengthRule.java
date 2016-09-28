package com.tchepannou.kiosk.core.rule;

public class TextLengthRule implements TextRule {
    private final int minLength;

    public TextLengthRule(final int minLength) {
        this.minLength = minLength;
    }

    @Override
    public Validation validate(final String text) {
        return text.length() > minLength
                ? Validation.success()
                : Validation.failure(getClass().getSimpleName());
    }
}
