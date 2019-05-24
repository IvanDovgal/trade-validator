package com.idovgal.tradevalidator.service.validator.impl.rules;

import com.google.common.collect.ImmutableList;
import com.idovgal.tradevalidator.service.validator.RuleValidationResult;
import com.idovgal.tradevalidator.service.validator.trade.AmericanVanillaOptionTrade;
import com.idovgal.tradevalidator.service.validator.trade.EuropeanVanillaOptionTrade;
import com.idovgal.tradevalidator.service.validator.trade.SpotTrade;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author ivan
 */
public class OptionRuleTest {

    @Test
    public void appliedFor() {
        OptionRule optionRule = new OptionRule();
        assertFalse("Option rule can't be applied for spot trade", optionRule.appliedFor(new SpotTrade()));
        assertTrue("Option rule must be applied for european style option trade", optionRule.appliedFor(new EuropeanVanillaOptionTrade()));
    }

    @Test
    public void check() {
        OptionRule optionRule = new OptionRule();
        AmericanVanillaOptionTrade trade = new AmericanVanillaOptionTrade();
        trade.setExcerciseStartDate(LocalDate.of(2019, 1, 15));
        trade.setPremiumDate(LocalDate.of(2019, 1, 10));
        trade.setExpiryDate(LocalDate.of(2019, 1, 16));
        trade.setTradeDate(LocalDate.of(2019, 1, 10));
        trade.setDeliveryDate(LocalDate.of(2019, 1, 22));
        RuleValidationResult check = optionRule.check(trade);
        assertTrue("This is valid option dates", check.isSuccess());
        trade.setExpiryDate(LocalDate.of(2019, 1, 10));
        check = optionRule.check(trade);
        assertFalse("This expire date must break rule", check.isSuccess());
        assertThat("Rule error fields is excerciseStartDate, expiryDate, tradeDate", check.getErrors().iterator().next().getFields(), is(ImmutableList.of("excerciseStartDate", "expiryDate", "tradeDate")));
        trade.setDeliveryDate(LocalDate.of(2019, 1, 10));
        trade.setExpiryDate(LocalDate.of(2019, 1, 16));
        check = optionRule.check(trade);
        assertFalse("This delivery date must break rule", check.isSuccess());
        assertThat("Rule error fields is expiryDate, premiumDate, deliveryDate", check.getErrors().iterator().next().getFields(), is(ImmutableList.of("expiryDate", "premiumDate", "deliveryDate")));

    }
}