package com.tchepannou.kiosk.core.filter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TrimFilterTest {

    @Test
    public void shouldRemoveLeadingSlash() throws Exception {
        final TrimFilter filter = new TrimFilter();

        assertThat(filter.filter("|hello")).isEqualTo("hello");
    }

    @Test
    public void shouldNotFilter() throws Exception {
        final TrimFilter filter = new TrimFilter();

        assertThat(filter.filter("hello")).isEqualTo("hello");
    }

}
