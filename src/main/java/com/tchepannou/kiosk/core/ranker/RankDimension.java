package com.tchepannou.kiosk.core.ranker;

import java.util.Comparator;

public interface RankDimension<T> {
    float getWeight();
    Comparator<T> getComparator();
}
