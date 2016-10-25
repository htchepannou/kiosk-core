package com.tchepannou.kiosk.core.text.fr;

import com.tchepannou.kiosk.core.text.Stemmer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FrenchStemmerTest {

    @Test
    public void testStem() throws Exception {
        final Stemmer stemmer = new FrenchStemmer();

        assertEquals("cheval", stemmer.stem("chevaux"));
        assertEquals("cameroun", stemmer.stem("camerounais"));
        assertEquals("camerounais", stemmer.stem("camerounaise"));
        assertEquals("poux", stemmer.stem("poux"));
        assertEquals("invest", stemmer.stem("investissement"));
        assertEquals("invest", stemmer.stem("investir"));
        assertEquals("fin", stemmer.stem("finissant"));
        assertEquals("dessin", stemmer.stem("dessinateur"));
        assertEquals("dessin", stemmer.stem("dessinatrice"));
        assertEquals("verif", stemmer.stem("verificateur"));
        assertEquals("verif", stemmer.stem("verificatrice"));
        assertEquals("comment", stemmer.stem("commentateur"));
        assertEquals("comment", stemmer.stem("commentatrice"));
        assertEquals("utilis", stemmer.stem("utilisateur"));
        assertEquals("format", stemmer.stem("formation"));
        assertEquals("pois", stemmer.stem("pois"));
        assertEquals("even", stemmer.stem("evenement"));
        assertEquals("troisiem", stemmer.stem("troisième"));
        assertEquals("institutric", stemmer.stem("institutrice"));
        assertEquals("instituteur", stemmer.stem("instituteur"));
        assertEquals("menteur", stemmer.stem("menteur"));
        assertEquals("menteux", stemmer.stem("menteuse"));
        assertEquals("commun", stemmer.stem("communicateur"));
        assertEquals("commun", stemmer.stem("communicatrice"));
        assertEquals("moll", stemmer.stem("molle"));
    }
}
