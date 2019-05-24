package com.idovgal.tradevalidator.rs.validation.view;

import com.idovgal.tradevalidator.service.validator.RuleDescription;
import com.idovgal.tradevalidator.service.validator.RuleValidationResult;
import com.idovgal.tradevalidator.service.validator.TradeValidationResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * View for single trade validation
 * @author ivan
 */
public class ValidationResultView {

    private final List<RuleDescription> rules;
    private final List<RuleValidationResult.ErrorDescription> errors;

    public ValidationResultView(List<RuleDescription> rules, List<RuleValidationResult.ErrorDescription> errors) {
        this.rules = rules;
        this.errors = errors;
    }

    public static ValidationResultView of(TradeValidationResult tradeValidationResult) {
        List<RuleValidationResult.ErrorDescription> errors = getErrorDescriptions(tradeValidationResult);
        List<RuleDescription> rules = getRuleDescriptions(tradeValidationResult);
        return new ValidationResultView(rules, errors);
    }

    protected static List<RuleDescription> getRuleDescriptions(TradeValidationResult tradeValidationResult) {
        return tradeValidationResult.getRules().stream()
                .map(RuleValidationResult::getRule)
                .collect(Collectors.toList());
    }

    protected static List<RuleValidationResult.ErrorDescription> getErrorDescriptions(TradeValidationResult tradeValidationResult) {
        return tradeValidationResult.getRules().stream()
                .map(RuleValidationResult::getErrors)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<RuleDescription> getRules() {
        return rules;
    }

    public List<RuleValidationResult.ErrorDescription> getErrors() {
        return errors;
    }

}
