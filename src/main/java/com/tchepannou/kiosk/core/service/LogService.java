package com.tchepannou.kiosk.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

    private final Map<String, Object> values = Collections.synchronizedMap(new HashMap<>());

    private TimeService time;
    private final Logger logger;

    public void add(final String key, final Object value) {
        if (value == null){
            return;
        }

        values.put(key, value);
    }

    public LogService(TimeService time) {
        this(LOGGER, time);
    }

    LogService(final Logger logger, final TimeService time) {
        this.logger = logger;
        this.time = time;
    }

    @Override
    public String toString() {
        final List<String> keys = values.keySet().stream().sorted().collect(Collectors.toList());

        final StringBuilder sb = new StringBuilder();
        for (final String key : keys) {
            final String value = encode(values.get(key));
            if (sb.length()> 0){
                sb.append(' ');
            }
            sb.append(key).append('=').append(value);
        }

        return sb.toString();
    }

    public void log() {
        logger.info(toString());
        values.clear();
    }

    public void log(final Throwable ex) {
        logger.info(toString(), ex);
        values.clear();
    }

    private String encode(final Object value) {
        String str;
        if (value instanceof Date) {
            str = toString((Date)value);
        } else if (value instanceof Collection){
            str = toString((Collection)value);
        } else {
            str = value.toString();
        }

        str = str
                .replace('\n', '.')
                .replace('"', '\'');

        return str.indexOf(' ') > 0 || str.indexOf('.') > 0 || str.indexOf('\'') > 0
            ? String.format("\"%s\"", str)
            : str;
    }

    private String toString(final Date value){
        return time.format(value);
    }

    private String toString(final Collection values){
        final StringBuilder sb = new StringBuilder();
        for (final Iterator it=values.iterator() ; it.hasNext() ; ){
            if (sb.length()>0){
                sb.append(", ");
            }
            sb.append(it.next().toString());
        }
        return sb.toString();
    }
}
