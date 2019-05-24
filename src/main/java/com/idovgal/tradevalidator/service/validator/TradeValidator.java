package com.idovgal.tradevalidator.service.validator;

import com.idovgal.tradevalidator.service.validator.trade.Trade;

/**
 * Interface for trade validator, which call multiple rules
 *
 * @author ivan
 */
public interface TradeValidator {

    /**
     * @param trade trade for validation
     * @return validation result of given trade
     */
    TradeValidationResult check(Trade trade);

}
