package com.idovgal.tradevalidator.service.validator;

import java.util.List;

/**
 * Trade validation result, contains result of validation rules
 *
 * @author ivan
 */
public class TradeValidationResult {

    private final List<RuleValidationResult> rules;

    public TradeValidationResult(List<RuleValidationResult> rules) {
        this.rules = rules;
    }

    public static TradeValidationResult create(List<RuleValidationResult> rules) {
        return new TradeValidationResult(rules);
    }

    /**
     * @return Rules results
     */
    public List<RuleValidationResult> getRules() {
        return rules;
    }
}
