package com.idovgal.tradevalidator.service.validator.impl.rules;

import com.google.common.collect.ImmutableList;
import com.idovgal.tradevalidator.service.validator.RuleValidationResult;
import com.idovgal.tradevalidator.service.validator.trade.AmericanVanillaOptionTrade;
import com.idovgal.tradevalidator.service.validator.trade.SpotTrade;
import com.idovgal.tradevalidator.service.validator.trade.Trade;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author ivan
 */
public class CurrenciesRuleTest {

    @Test
    public void validateCurrencies() {
        CurrenciesRule currenciesRule = new CurrenciesRule();
        AmericanVanillaOptionTrade trade = new AmericanVanillaOptionTrade();
        trade.setPayCcy("RUB");
        trade.setPremiumCcy("USD");
        assertTrue("RUB and USD is valid currency", currenciesRule.validateCurrencies(trade.getCurrencies()).isEmpty());
        trade.setPremiumCcy("US");
        assertFalse("US is not valid currency", currenciesRule.validateCurrencies(trade.getCurrencies()).isEmpty());
    }

    @Test
    public void validateCurrencyPairs() {
        CurrenciesRule currenciesRule = new CurrenciesRule();
        Trade trade = new SpotTrade();
        trade.setCcyPair("RUBUSD");
        assertTrue("RUBUSD is valid ccyPair", currenciesRule.validateCurrencyPairs(trade.getCurrencyPairs()).isEmpty());
        trade.setCcyPair("RUUSD");
        assertFalse("RUUSD is not valid ccyPair", currenciesRule.validateCurrencyPairs(trade.getCurrencyPairs()).isEmpty());
    }

    @Test
    public void check() {
        CurrenciesRule currenciesRule = new CurrenciesRule();
        AmericanVanillaOptionTrade trade = new AmericanVanillaOptionTrade();
        trade.setPayCcy("RUB");
        trade.setPremiumCcy("USD");
        trade.setCcyPair("RUBUSD");
        assertTrue("All currencies is valid", currenciesRule.check(trade).isSuccess());
        trade.setPayCcy("RU");
        RuleValidationResult check = currenciesRule.check(trade);
        assertFalse("RU is not valid currency", check.isSuccess());
        assertEquals("Rule must return 1 error", check.getErrors().size(), 1);
        assertThat("Rule error field is payCcy", check.getErrors().iterator().next().getFields(), is(ImmutableList.of("payCcy")));
    }
}