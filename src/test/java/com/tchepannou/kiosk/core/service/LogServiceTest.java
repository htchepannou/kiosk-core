package com.tchepannou.kiosk.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogServiceTest {
    @Mock
    private TimeService time;

    @Mock
    private Logger logger;

    @InjectMocks
    private LogService log;

    @Test
    public void shouldOutputToLogger (){
        // Given
        log = new LogService(logger, time);
        final LogService service = spy(log);
        when(service.toString()).thenReturn("foo");

        // Then
        service.log();

        // Then
        verify(logger).info("foo");
    }

    @Test
    public void shouldOutputExceptionToLogger (){
        // Given
        log = new LogService(logger, time);
        final LogService service = spy(log);
        when(service.toString()).thenReturn("foo");

        final Exception ex = new RuntimeException();

        // Then
        service.log(ex);

        // Then
        verify(logger).info("foo", ex);
    }

    @Test
    public void shouldNeverLogNull() {
        // Given
        log.add("foo", null);

        // Given
        final String value = log.toString();

        // Then
        assertThat(value).isEmpty();
    }

    @Test
    public void shouldLogDate() {
        // Given
        when(time.format(Matchers.any())).thenReturn("2011-01-12 05:30:51 -0500");

        log.add("foo", new Date());

        // Given
        final String value = log.toString();

        // Then
        assertThat(value).isEqualTo("foo=\"2011-01-12 05:30:51 -0500\"");
    }

    @Test
    public void shouldLogString() {
        // Given
        log.add("foo", "bar");

        // Given
        final String value = log.toString();

        // Then
        assertThat(value).isEqualTo("foo=bar");
    }

    @Test
    public void shouldLogStringWithSpace() {
        // Given
        log.add("foo", "john doe");

        // Given
        final String value = log.toString();

        // Then
        assertThat(value).isEqualTo("foo=\"john doe\"");
    }

    @Test
    public void shouldLogStringWithDQuote() {
        // Given
        log.add("foo", "john\"doe");

        // Given
        final String value = log.toString();

        // Then
        assertThat(value).isEqualTo("foo=\"john'doe\"");
    }

    @Test
    public void shouldLogStringWithCR() {
        // Given
        log.add("foo", "john\ndoe");

        // Given
        final String value = log.toString();

        // Then
        assertThat(value).isEqualTo("foo=\"john.doe\"");
    }

    @Test
    public void shouldLogCollection() {
        // Given
        log.add("foo", Arrays.asList("a", "B", "c"));

        // Given
        final String value = log.toString();

        // Then
        assertThat(value).isEqualTo("foo=\"a, B, c\"");
    }

    @Test
    public void shouldSortKeys() {
        // Given
        log.add("Z", "foo");
        log.add("A", "bar");

        // Given
        final String value = log.toString();

        // Then
        assertThat(value).isEqualTo("A=bar Z=foo");
    }
}
