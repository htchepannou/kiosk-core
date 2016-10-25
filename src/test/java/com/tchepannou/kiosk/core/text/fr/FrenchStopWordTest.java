package com.tchepannou.kiosk.core.text.fr;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FrenchStopWordTest {
    @Test
    public void testIs() throws Exception {
        FrenchStopWord stopWords = new FrenchStopWord();

        assertThat(stopWords.is("le"), equalTo(true));
        assertThat(stopWords.is("machine"), equalTo(false));

    }

    @Test
    public void testIs_CaseInsensitive() throws Exception {
        FrenchStopWord stopWords = new FrenchStopWord();

        assertThat(stopWords.is("un"), equalTo(true));
        assertThat(stopWords.is("Un"), equalTo(true));
    }
}
