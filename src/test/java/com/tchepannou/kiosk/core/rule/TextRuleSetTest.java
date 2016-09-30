package com.tchepannou.kiosk.core.rule;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TextRuleSetTest {

    @Test
    public void testValidateSuccess() throws Exception {
        // Given
        final TextRule rule1 = mock(TextRule.class);
        when(rule1.validate(any())).thenReturn(Validation.success());

        final TextRule rule2 = mock(TextRule.class);
        when(rule2.validate(any())).thenReturn(Validation.success());

        final TextRuleSet rules = new TextRuleSet(Arrays.asList(rule1, rule2));

        // When
        Validation result = rules.validate("fdfd");

        // Then
        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    public void testValidateFailure() throws Exception {
        // Given
        final TextRule rule1 = mock(TextRule.class);
        when(rule1.validate(any())).thenReturn(Validation.failure("FAIL"));

        final TextRule rule2 = mock(TextRule.class);
        when(rule2.validate(any())).thenReturn(Validation.success());

        final TextRuleSet rules = new TextRuleSet(Arrays.asList(rule1, rule2));

        // When
        Validation result = rules.validate("fdfd");

        // Then
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getReason()).isEqualTo("FAIL");
    }
}
