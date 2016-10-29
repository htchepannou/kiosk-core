package com.tchepannou.kiosk.core.text;

import com.tchepannou.kiosk.core.text.fr.FrenchTextKit;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FragmentTest {
    @Test
    public void testParse() {
        final String text = "Je suis allé hier, manger du steak chez Toqué. Je l'ai plutot aimé!";
        final TextKit kit = new FrenchTextKit();

        final List<Fragment> fragments = Fragment.parse(text, kit);
        assertThat(fragments).hasSize(3);
        assertThat(fragments.get(0).toString()).isEqualTo("alle hier");
        assertThat(fragments.get(1).toString()).isEqualTo("manger steak toque");
        assertThat(fragments.get(2).toString()).isEqualTo("plutot aime");
    }

    @Test
    public void testExtractKeywords() {
        final String text = "Je suis allé hier manger du steak chez Toqué!!";
        final TextKit kit = new FrenchTextKit();
        final List<Fragment> fragments = Fragment.parse(text, kit);

        final List<String> keywords = fragments.get(0).extractKeywords(3);

        assertThat(keywords).containsExactly(
                "alle", "alle hier", "alle hier manger",
                "hier", "hier manger", "hier manger steak",
                "manger", "manger steak", "manger steak toque",
                "steak", "steak toque",
                "toque"
        );
    }
}
