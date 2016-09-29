package com.tchepannou.kiosk.core.filter;

import java.util.List;

public class FilterSet<T> implements Filter<T> {
    private final List<Filter<T>> filters;

    public FilterSet(final List<Filter<T>> filters) {
        this.filters = filters;
    }

    @Override
    public T filter(final T str) {
        T txt = str;
        for (final Filter<T> filter : filters) {
            txt = filter.filter(txt);
        }
        return txt;
    }
}
