package com.tchepannou.kiosk.core.text.fr;

import com.tchepannou.kiosk.core.text.StopWord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class FrenchStopWord implements StopWord{
    //-- Attributes
    private Set<String> words = new HashSet<>();

    public FrenchStopWord(){
        this(FrenchStopWord.class.getResourceAsStream("/text/fr/stopwords.txt"));
    }
    protected FrenchStopWord(InputStream in){
        try {
            load(in);
        } catch (IOException e){
            throw new IllegalStateException("Unable to load stopwords", e);
        }
    }

    //-- Public
    protected void load (InputStream in) throws IOException {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
            String text;
            while ((text = reader.readLine()) != null){
                words.add(text.trim().toLowerCase());
            }
        }
    }

    //-- StopWords implementation
    @Override
    public boolean is (String word){
        return words.contains(word.toLowerCase());
    }
}
