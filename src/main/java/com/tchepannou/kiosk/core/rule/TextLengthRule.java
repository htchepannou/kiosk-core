package com.tchepannou.kiosk.core.rule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TextLengthRule implements TextRule {
    private final int minLength;

    public TextLengthRule(final int minLength) {
        this.minLength = minLength;
    }

    @Override
    public Validation validate(final String text) {
        Document doc = Jsoup.parse(text);
        return doc.body().text().length() > minLength
                ? Validation.success()
                : Validation.failure(getClass().getSimpleName());
    }
}
