package com.tchepannou.kiosk.core.text;

import com.tchepannou.kiosk.core.text.fr.FrenchTextKit;

import java.util.HashMap;
import java.util.Map;

public class TextKitProvider {
    private final Map<String, TextKit> kits;

    public TextKitProvider(){
        kits = new HashMap<>();
        kits.put("fr", new FrenchTextKit());
    }

    public TextKit get (final String languageCode){
        final TextKit kit = languageCode != null ? kits.get(languageCode.toLowerCase()) : null;
        if (kit == null){
            throw new IllegalStateException("Not TextKit available for " + languageCode);
        }
        return kit;
    }
}
