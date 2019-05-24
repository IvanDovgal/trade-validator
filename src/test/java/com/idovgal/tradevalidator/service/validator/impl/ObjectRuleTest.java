package com.idovgal.tradevalidator.service.validator.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.idovgal.tradevalidator.service.validator.RuleValidationResult;
import com.idovgal.tradevalidator.service.validator.trade.AmericanVanillaOptionTrade;
import com.idovgal.tradevalidator.service.validator.trade.SpotTrade;
import com.idovgal.tradevalidator.service.validator.trade.Trade;
import com.idovgal.tradevalidator.service.validator.trade.VanillaOptionTrade;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author ivan
 */
public class ObjectRuleTest {

    @Test
    public void check() {
        Validator validator = mock(Validator.class);
        when(validator.validate(any(VanillaOptionTrade.class))).thenReturn(ImmutableSet.of());
        ConstraintViolation constraintViolation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);
        when(path.toString()).thenReturn("tradeDate");
        when(constraintViolation.getPropertyPath()).thenReturn(path);
        when(constraintViolation.getMessage()).thenReturn("tradeDate can not be null");
        when(validator.validate(any(SpotTrade.class))).thenReturn(ImmutableSet.of(
                constraintViolation
        ));
        ObjectRule objectRule = new ObjectRule(validator);
        Trade trade = new SpotTrade();
        RuleValidationResult check = objectRule.check(trade);
        assertFalse("Rule check must be failed", check.isSuccess());
        assertThat("Rule error fields is tradeDate", check.getErrors().iterator().next().getFields(), is(ImmutableList.of("tradeDate")));
        trade = new AmericanVanillaOptionTrade();
        check = objectRule.check(trade);
        assertTrue("Rule check must be success", check.isSuccess());
    }
}