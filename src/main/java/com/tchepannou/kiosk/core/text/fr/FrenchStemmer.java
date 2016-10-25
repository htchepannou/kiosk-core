package com.tchepannou.kiosk.core.text.fr;

import com.tchepannou.kiosk.core.text.Stemmer;
import com.tchepannou.kiosk.core.text.ext.SnowballStemmer;
import com.tchepannou.kiosk.core.text.ext.frenchStemmer;

public class FrenchStemmer implements Stemmer {
    SnowballStemmer stemmer = new frenchStemmer();

    @Override
    public String stem(final String word) {
        stemmer.setCurrent(word);
        stemmer.stem();
        return stemmer.getCurrent();
    }

}
