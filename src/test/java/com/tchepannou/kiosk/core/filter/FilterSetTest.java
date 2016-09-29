package com.tchepannou.kiosk.core.filter;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilterSetTest {

    @Test
    public void testFilter() throws Exception {
        // Given
        final Filter f1 = mock(Filter.class);
        when(f1.filter(any())).thenReturn("a");

        final Filter f2 = mock(Filter.class);
        when(f2.filter(any())).thenReturn("b");

        final Filter f3 = mock(Filter.class);
        when(f3.filter(any())).thenReturn("c");

        // When
        final Filter fs = new FilterSet(Arrays.asList(f1, f2, f3));

        // Then
        assertThat(fs.filter("hello world")).isEqualTo("c");
    }
}
