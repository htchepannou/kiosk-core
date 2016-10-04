package com.tchepannou.kiosk.core.ranker;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RankEntryTest {

    @Test
    public void testCompareToLT() throws Exception {
        final RankEntry e1 = new RankEntry(createRankable());
        e1.setRank(createDimension(1f), 1);

        final RankEntry e2 = new RankEntry(createRankable());
        e2.setRank(createDimension(1f), 5);

        assertThat(e1.compareTo(e2)).isEqualTo(-400);
    }

    @Test
    public void testCompareToCT() throws Exception {
        final RankEntry e1 = new RankEntry(createRankable());
        e1.setRank(createDimension(1f), 6);

        final RankEntry e2 = new RankEntry(createRankable());
        e2.setRank(createDimension(1f), 5);

        assertThat(e1.compareTo(e2)).isEqualTo(100);
    }

    @Test
    public void testCompareToEQ() throws Exception {
        final RankEntry e1 = new RankEntry(createRankable());
        e1.setRank(createDimension(1f), 1);

        final RankEntry e2 = new RankEntry(createRankable());
        e2.setRank(createDimension(1f), 1);

        assertThat(e1.compareTo(e2)).isEqualTo(0);

    }



    @Test
    public void testGetFinalRank() throws Exception {
        final RankEntry entry = new RankEntry(createRankable());
        entry.setRank(createDimension(.1f), 1);
        entry.setRank(createDimension(.3f), 5);
        entry.setRank(createDimension(.6f), 3);

        assertThat(entry.getFinalRank()).isEqualTo(3.4f);
    }


    private RankDimension createDimension(final float weight) {
        final RankDimension dim = mock(RankDimension.class);
        when(dim.getWeight()).thenReturn(weight);
        return dim;
    }

    private Rankable createRankable() {
        return mock(Rankable.class);
    }
}
