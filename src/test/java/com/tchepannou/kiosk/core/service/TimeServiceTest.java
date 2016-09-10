package com.tchepannou.kiosk.core.service;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeServiceTest {
    private TimeService service = new TimeService();

    @Test
    public void showReturnNow(){
        final Date now = service.now();

        assertThat(System.currentTimeMillis() - now.getTime()).isBetween(0L, 10L);
    }

    @Test
    public void shouldGenerateDateYYMMDD(){
        // GIVEN
        final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

        // WHEN
        final Date date = service.toDate(2011, 01, 12);

        // THEN
        assertThat(fmt.format(date)).isEqualTo("2011-01-11 19:00:00 -0500");
    }

    @Test
    public void shouldGenerateDateYYMMDDHHMM(){
        // GIVEN
        final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

        // WHEN
        final Date date = service.toDate(2011, 01, 12, 10, 30, 51);

        // THEN
        assertThat(fmt.format(date)).isEqualTo("2011-01-12 05:30:51 -0500");
    }

    @Test
    public void shouldFormatDate(){
        // WHEN
        final Date date = service.toDate(2011, 01, 12, 10, 30, 51);

        // THEN
        assertThat(service.format(date)).isEqualTo("2011-01-12 05:30:51 -0500");
    }
}
