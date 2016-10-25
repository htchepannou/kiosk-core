package com.tchepannou.kiosk.core.text.impl;

import com.tchepannou.kiosk.core.text.Tokenizer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LowerCaseTokeninerTest {

    @Test
    public void testNextToken() throws Exception {
        Tokenizer tokenizer = mock(Tokenizer.class);
        when(tokenizer.nextToken()).thenReturn("ALLO WORLD");

        assertThat(new LowerCaseTokeniner(tokenizer).nextToken()).isEqualTo("allo world");
    }
}
