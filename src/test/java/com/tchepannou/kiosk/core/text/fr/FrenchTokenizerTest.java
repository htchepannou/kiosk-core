package com.tchepannou.kiosk.core.text.fr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FrenchTokenizerTest {
    @Test
    public void testNextToken() throws Exception {
        final String str = "C’est une histoire morbide!!! Jean-Paul et Boko Haram.";

        final FrenchTokenizer tokenizer = new FrenchTokenizer(str);

        assertEquals("C", tokenizer.nextToken());
        assertEquals("’", tokenizer.nextToken());
        assertEquals("est", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("une", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("histoire", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("morbide", tokenizer.nextToken());
        assertEquals("!", tokenizer.nextToken());
        assertEquals("!", tokenizer.nextToken());
        assertEquals("!", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("Jean-Paul", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("et", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("Boko", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("Haram", tokenizer.nextToken());
        assertEquals(".", tokenizer.nextToken());
    }

    @Test
    public void testNextToken_DotDocDot() throws Exception {
        final String str = "Hello BIYA...I love you!";

        final FrenchTokenizer tokenizer = new FrenchTokenizer(str);

        assertEquals("Hello", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("BIYA", tokenizer.nextToken());
        assertEquals("...", tokenizer.nextToken());
        assertEquals("I", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("love", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("you", tokenizer.nextToken());
        assertEquals("!", tokenizer.nextToken());
    }

    @Test
    public void testNextToken_RoundBracket() throws Exception {
        final String str = "Après le (RDC) voici!";

        final FrenchTokenizer tokenizer = new FrenchTokenizer(str);

        assertEquals("Après", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("le", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("(", tokenizer.nextToken());
        assertEquals("RDC", tokenizer.nextToken());
        assertEquals(")", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("voici", tokenizer.nextToken());
        assertEquals("!", tokenizer.nextToken());
    }

    @Test
    public void testNextToken_SquereBracket() throws Exception {
        final String str = "Après le [RDC] voici!";

        final FrenchTokenizer tokenizer = new FrenchTokenizer(str);

        assertEquals("Après", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("le", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("[", tokenizer.nextToken());
        assertEquals("RDC", tokenizer.nextToken());
        assertEquals("]", tokenizer.nextToken());
        assertEquals(" ", tokenizer.nextToken());
        assertEquals("voici", tokenizer.nextToken());
        assertEquals("!", tokenizer.nextToken());
    }
}
