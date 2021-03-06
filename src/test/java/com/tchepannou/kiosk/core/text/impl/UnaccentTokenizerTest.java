package com.tchepannou.kiosk.core.text.impl;

import com.tchepannou.kiosk.core.text.Tokenizer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnaccentTokenizerTest {

    @Test
    public void testNextToken() throws Exception {
        final Tokenizer tokenizer = mock(Tokenizer.class);
        when(tokenizer.nextToken()).thenReturn("L’agglomération de Paris. Marché de Noël de Strasbourg - cours de grammaire française");

        assertThat(new UnaccentTokenizer(tokenizer).nextToken()).isEqualTo("L’agglomeration de Paris. Marche de Noel de Strasbourg - cours de grammaire francaise");
    }

    @Test
    public void testNextToken2() throws Exception {
        final Tokenizer tokenizer = mock(Tokenizer.class);
        when(tokenizer.nextToken()).thenReturn("je");

        assertThat(new UnaccentTokenizer(tokenizer).nextToken()).isEqualTo("je");
    }

    @Test
    public void testNextTokenNull() throws Exception {
        final Tokenizer tokenizer = mock(Tokenizer.class);
        when(tokenizer.nextToken()).thenReturn(null);

        assertThat(new UnaccentTokenizer(tokenizer).nextToken()).isNull();
    }
}
