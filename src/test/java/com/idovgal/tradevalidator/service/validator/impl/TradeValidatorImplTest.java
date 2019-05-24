package com.idovgal.tradevalidator.service.validator.impl;

import com.google.common.collect.ImmutableList;
import com.idovgal.tradevalidator.service.validator.Rule;
import com.idovgal.tradevalidator.service.validator.RuleDescription;
import com.idovgal.tradevalidator.service.validator.RuleValidationResult;
import com.idovgal.tradevalidator.service.validator.TradeValidationResult;
import com.idovgal.tradevalidator.service.validator.trade.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author ivan
 */
public class TradeValidatorImplTest {

    @Test
    public void check() {
        ObjectRule objectRule = mock(ObjectRule.class);
        when(objectRule.appliedFor(anyObject())).thenReturn(true);
        when(objectRule.check(any(Trade.class))).thenReturn(RuleValidationResult.ok(RuleDescription.create("object-rule", "description")));
        when(objectRule.check(any(ForwardTrade.class))).thenReturn(RuleValidationResult.fail(RuleDescription.create("object-rule", "description"), RuleValidationResult.errorDescription("some bean error error", "tradeDate")));
        Rule rule1 = mock(Rule.class);
        when(rule1.appliedFor(anyObject())).thenReturn(true);
        when(rule1.check(any(Trade.class))).thenReturn(RuleValidationResult.ok(RuleDescription.create("rule1", "description")));
        when(rule1.check(any(SpotTrade.class))).thenReturn(RuleValidationResult.fail(RuleDescription.create("rule1", "description"), RuleValidationResult.errorDescription("some bean error error", "tradeDate")));
        Rule rule2 = mock(Rule.class);
        when(rule2.appliedFor(anyObject())).thenReturn(true);
        when(rule2.check(any(Trade.class))).thenReturn(RuleValidationResult.ok(RuleDescription.create("rule2", "description")));
        when(rule2.check(any(VanillaOptionTrade.class))).thenReturn(RuleValidationResult.fail(RuleDescription.create("rule2", "description"), RuleValidationResult.errorDescription("some bean error error", "tradeDate")));
        List<Rule> ruleList = ImmutableList.of(rule1, rule2);
        TradeValidatorImpl tradeValidator = new TradeValidatorImpl(ruleList, objectRule);
        tradeValidator.check(new ForwardTrade());
        verify(rule1, times(0)).check(anyObject());
        verify(rule2, times(0)).check(anyObject());
        TradeValidationResult check = tradeValidator.check(new SpotTrade());
        assertEquals("Only one rule should be failed", check.getRules().size(), 1);
        assertEquals("Only rule1 should be failed", check.getRules().iterator().next().getRule().getName(), "rule1");
        verify(rule1, times(1)).check(anyObject());
        verify(rule2, times(1)).check(anyObject());
        check = tradeValidator.check(new AmericanVanillaOptionTrade());
        assertEquals("Only one rule should be failed", check.getRules().size(), 1);
        assertEquals("Only rule2 should be failed", check.getRules().iterator().next().getRule().getName(), "rule2");
        verify(rule1, times(2)).check(anyObject());
        verify(rule2, times(2)).check(anyObject());


    }
}