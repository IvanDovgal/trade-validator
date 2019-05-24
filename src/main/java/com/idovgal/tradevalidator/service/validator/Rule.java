package com.idovgal.tradevalidator.service.validator;

import com.idovgal.tradevalidator.service.validator.trade.Trade;

/**
 * Interface for a trade check rule
 * @author ivan
 */
public interface Rule {

    /**
     * @return description of rule
     */
    RuleDescription description();

    /**
     * determine that the current rule can be applied for given trade
     * @param trade trade for checking
     * @return true if given rule can be applied for given trade
     */
    boolean appliedFor(Trade trade);

    /**
     * @param trade trade for validation
     * @return rule validation result of given trade
     */
    RuleValidationResult check(Trade trade);

}
