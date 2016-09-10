package com.tchepannou.kiosk.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LogServiceTest {
    @Mock
    private TimeService time;

    @InjectMocks
    private LogService log;

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
        Mockito.when(time.format(Matchers.any())).thenReturn("2011-01-12 05:30:51 -0500");

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
