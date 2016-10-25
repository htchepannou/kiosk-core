package com.tchepannou.kiosk.core.text.fr;

import com.tchepannou.kiosk.core.text.Stemmer;
import com.tchepannou.kiosk.core.text.StopWord;
import com.tchepannou.kiosk.core.text.TextKit;
import com.tchepannou.kiosk.core.text.Tokenizer;
import com.tchepannou.kiosk.core.text.impl.LowerCaseTokeniner;
import com.tchepannou.kiosk.core.text.impl.UnaccentTokenizer;

public class FrenchTextKit implements TextKit {
    final Stemmer stemmer = new FrenchStemmer();
    final StopWord stopWord = new FrenchStopWord();

    @Override
    public Stemmer getStemmer() {
        return stemmer;
    }

    @Override
    public StopWord getStopWord() {
        return stopWord;
    }

    @Override
    public Tokenizer getTokenizer(final String text) {
        return new UnaccentTokenizer(
                new LowerCaseTokeniner(
                        new FrenchTokenizer(text)
                )
        );
    }
}
