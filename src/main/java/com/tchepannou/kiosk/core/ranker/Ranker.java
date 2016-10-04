package com.tchepannou.kiosk.core.ranker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ranker<T> {
    private final List<RankDimension<T>> dimensions;

    public Ranker(final List<RankDimension<T>> dimensions) {
        this.dimensions = dimensions;
    }

    public List<RankEntry<T>> rank(final List<T> rankables) {
        final Map<T, RankEntry<T>> entries = new HashMap<>();
        for (final RankDimension<T> dimension : dimensions) {
            sort(rankables, entries, dimension);
        }

        final List<RankEntry<T>> result = new ArrayList<>(entries.values());
        Collections.sort(result);
        return result;
    }

    private void sort(final List<T> rankables, final Map<T, RankEntry<T>> entries, final RankDimension<T> dimension) {
        final List<T> items = new ArrayList<>(rankables);
        Collections.sort(items, dimension.getComparator());

        int i = 1;
        for (final T item : items){
            final RankEntry entry = entries.containsKey(item) ? entries.get(item) : new RankEntry(item);
            entry.setRank(dimension, i++);
        }
    }
}
