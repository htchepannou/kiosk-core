package com.tchepannou.kiosk.core.rule;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TextLengthRuleTest {

    @Test
    public void testValidateSuccess() throws Exception {
        TextRule rule = new TextLengthRule(4);

        assertThat(rule.validate("<p>12345</p>").isSuccess()).isTrue();
    }

    @Test
    public void testValidateFailure() throws Exception {
        TextRule rule = new TextLengthRule(7);

        assertThat(rule.validate("<p>12345</p>").isSuccess()).isFalse();
        assertThat(rule.validate("<p>12345</p>").getReason()).isEqualTo("TextLengthRule");
    }
}
