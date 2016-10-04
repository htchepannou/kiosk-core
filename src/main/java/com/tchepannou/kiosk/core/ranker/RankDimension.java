package com.tchepannou.kiosk.core.ranker;

import java.util.Comparator;

public interface RankDimension {
    float getWeight();
    Comparator<? extends Rankable> getComparator();
}
