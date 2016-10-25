package com.tchepannou.kiosk.core.text.fr;

import com.tchepannou.kiosk.core.text.Tokenizer;

public class FrenchTokenizer implements Tokenizer {
    private static final String DELIM = ".,?!;:'\"’-+/* \n\r\t«»“”()[]";
    private char[] ch;
    private int pos;
    private int len;

    public FrenchTokenizer (String str){
        ch = str.toCharArray();
        pos = 0;
        len = str.length();
    }

    @Override
    public String nextToken (){
        int offset = pos;
        boolean stop = false;
        while (pos < len && !stop){
            char cur = ch[pos];

            if (DELIM.indexOf(cur) >= 0){
                if (cur=='-' && (pos -1>=0 && Character.isAlphabetic(ch[pos -1])) && (pos +1< len && Character.isAlphabetic(ch[pos +1]))) { // NOSONAR (expression too complex)
                    pos++;
                } else if (pos >offset){ // End of word
                    stop = true;
                } else {    // punctuation
                    if (cur=='.' && (pos +1< len && ch[pos +1]=='.') && (pos +2< len && ch[pos +2]=='.')){ /* ... */    // NOSONAR (expression too complex)
                        pos += 3;
                    }else {
                        pos++;
                    }
                    stop = true;
                }
            } else {
                ++pos;
            }
        }

        return pos > offset ? new String(ch, offset, pos -offset) : null;
    }

    public int getPosition (){
        return pos;
    }
}
