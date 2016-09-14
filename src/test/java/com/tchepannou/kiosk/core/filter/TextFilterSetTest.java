package com.tchepannou.kiosk.core.filter;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TextFilterSetTest {

    @Test
    public void testFilter() throws Exception {
        // Given
        final TextFilter f1 = mock(TextFilter.class);
        when(f1.filter(any())).thenReturn("a");

        final TextFilter f2 = mock(TextFilter.class);
        when(f2.filter(any())).thenReturn("b");

        final TextFilter f3 = mock(TextFilter.class);
        when(f3.filter(any())).thenReturn("c");

        // When
        final TextFilter fs = new TextFilterSet(Arrays.asList(f1, f2, f3));

        // Then
        assertThat(fs.filter("hello world")).isEqualTo("c");
    }
}
