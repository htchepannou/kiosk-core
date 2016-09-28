package com.tchepannou.kiosk.core.rule;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TextLengthRuleTest {

    @Test
    public void testValidateSuccess() throws Exception {
        TextRule rule = new TextLengthRule(4);

        assertThat(rule.validate("12345").isSuccess()).isTrue();
    }

    @Test
    public void testValidateFailure() throws Exception {
        TextRule rule = new TextLengthRule(7);

        assertThat(rule.validate("12345").isSuccess()).isFalse();
        assertThat(rule.validate("12345").getReason()).isEqualTo("TextLengthRule");
    }
}
