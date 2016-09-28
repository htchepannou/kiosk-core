package com.tchepannou.kiosk.core.rule;

import java.util.List;

public class TextRuleSet implements TextRule{
    private final List<TextRule> rules;

    public TextRuleSet(final List<TextRule> rules) {
        this.rules = rules;
    }

    @Override
    public Validation validate(final String text) {
        for (final TextRule rule : rules){
            Validation validation = rule.validate(text);
            if (!validation.isSuccess()){
                return validation;
            }
        }
        return Validation.success();
    }
}
