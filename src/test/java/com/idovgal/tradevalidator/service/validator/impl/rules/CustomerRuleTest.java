package com.idovgal.tradevalidator.service.validator.impl.rules;

import com.google.common.collect.ImmutableList;
import com.idovgal.tradevalidator.service.validator.RuleValidationResult;
import com.idovgal.tradevalidator.service.validator.trade.SpotTrade;
import com.idovgal.tradevalidator.service.validator.trade.Trade;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author ivan
 */
public class CustomerRuleTest {

    @Test
    public void check() {
        CustomerRule customerRule = new CustomerRule();
        Trade trade = new SpotTrade();
        trade.setCustomer("PLUTO2");
        assertTrue("PLUTO2 is valid customer", customerRule.check(trade).isSuccess());
        trade.setCustomer("PLUTO3");
        RuleValidationResult check = customerRule.check(trade);
        assertFalse("PLUTO3 is not valid customer", check.isSuccess());
        assertThat("Rule error field is customer", check.getErrors().iterator().next().getFields(), is(ImmutableList.of("customer")));
    }
}