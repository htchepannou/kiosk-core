package com.tchepannou.kiosk.core.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Fragment implements Iterable<String> {
    private static final String PUNCTUATION = ".,?!;:'";
    private final List<String> tokens = new LinkedList<>();

    //-- Public
    public List<String> extractKeywords(int n)
    {
        final List<String> items = new ArrayList(tokens.size());
        items.addAll(tokens);

        final List<String> result = new ArrayList<>();
        final int len = tokens.size();
        for (int i=0 ; i<len ; i++){
            final StringBuilder buff = new StringBuilder();

            for (int j=i ; j<Math.min(i+n, len) ; j++){
                if (buff.length() > 0){
                    buff.append(' ');
                }
                buff.append(tokens.get(j));
                result.add(buff.toString());
            }
        }
        return result;
    }

    public static List<Fragment> parse(final String text, final TextKit kit) {
        final List<Fragment> fragments = new ArrayList<>();
        final Tokenizer tokenizer = kit.getTokenizer(text);
        final StopWord stopWord = kit.getStopWord();

        String token = null;
        Fragment fragment = null;
        while ((token = tokenizer.nextToken()) != null) {
            if (PUNCTUATION.contains(token)) {
                newFragment(fragment, fragments);
                fragment = null;
            } else if (stopWord.is(token) || token.length() == 1) {   // Ignore stop words
                continue;
            } else {
                fragment = addToFragment(fragment, token);
            }
        }

        newFragment(fragment, fragments);
        return fragments;
    }

    private static void newFragment(final Fragment fragment, final List<Fragment> fragments) {
        if (fragment != null && fragment.size() > 0) {
            fragments.add(fragment);
        }
    }

    private static Fragment addToFragment(Fragment fragment, final String token) {
        if (fragment == null) {
            fragment = new Fragment();
        }
        fragment.add(token);
        return fragment;
    }

    public void add(final String token) {
        tokens.add(token);
    }

    public int size() {
        return tokens.size();
    }

    @Override
    public String toString (){
        return String.join(" ", tokens);
    }

    //-- Iterable overrides
    @Override
    public Iterator<String> iterator() {
        return tokens.iterator();
    }

    @Override
    public void forEach(final Consumer<? super String> action) {
        tokens.forEach(action);
    }

    @Override
    public Spliterator<String> spliterator() {
        return tokens.spliterator();
    }
}
