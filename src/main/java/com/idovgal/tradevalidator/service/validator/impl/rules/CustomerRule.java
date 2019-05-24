package com.idovgal.tradevalidator.service.validator.impl.rules;

import com.idovgal.tradevalidator.service.validator.Rule;
import com.idovgal.tradevalidator.service.validator.RuleDescription;
import com.idovgal.tradevalidator.service.validator.RuleValidationResult;
import com.idovgal.tradevalidator.service.validator.trade.Trade;
import org.springframework.stereotype.Component;

import static com.idovgal.tradevalidator.service.validator.RuleValidationResult.*;

/**
 * @author ivan
 */
@Component
public class CustomerRule implements Rule {
    @Override
    public boolean appliedFor(Trade trade) {
        return true;
    }

    @Override
    public RuleDescription description() {
        return RuleDescription.create("customer", "Supported counterparties (customers) are: PLUTO1, PLUTO2");
    }

    @Override
    public RuleValidationResult check(Trade trade) {
        String customer = trade.getCustomer();
        if (customer.equals("PLUTO1") || customer.equals("PLUTO2"))
            return ok(description());
        else
            return fail(description(), errorDescription("Customer can be only PLUTO1 and PLUTO2", "customer"));
    }
}
