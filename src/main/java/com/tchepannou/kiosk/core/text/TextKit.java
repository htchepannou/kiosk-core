package com.tchepannou.kiosk.core.text;

public interface TextKit {
    Stemmer getStemmer();
    StopWord getStopWord();
    Tokenizer getTokenizer(String text);
}
