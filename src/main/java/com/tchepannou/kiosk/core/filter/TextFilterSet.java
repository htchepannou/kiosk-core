package com.tchepannou.kiosk.core.filter;

import java.util.List;

public class TextFilterSet implements TextFilter {
    private final List<TextFilter> filters;

    public TextFilterSet(final List<TextFilter> filters) {
        this.filters = filters;
    }

    @Override
    public String filter(final String str) {
        String txt = str;
        for (final TextFilter filter : filters) {
            txt = filter.filter(txt);
        }
        return txt;
    }
}
