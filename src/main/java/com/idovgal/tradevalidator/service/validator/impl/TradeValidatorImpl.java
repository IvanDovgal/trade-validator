package com.idovgal.tradevalidator.service.validator.impl;

import com.google.common.collect.ImmutableList;
import com.idovgal.tradevalidator.service.validator.Rule;
import com.idovgal.tradevalidator.service.validator.RuleValidationResult;
import com.idovgal.tradevalidator.service.validator.TradeValidationResult;
import com.idovgal.tradevalidator.service.validator.TradeValidator;
import com.idovgal.tradevalidator.service.validator.trade.Trade;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ivan
 */
@Component
public class TradeValidatorImpl implements TradeValidator {

    final List<Rule> rules;

    final ObjectRule objectRule;

    public TradeValidatorImpl(List<Rule> rules, ObjectRule objectRule) {
        this.rules = rules;
        this.objectRule = objectRule;
    }

    @Override
    public TradeValidationResult check(Trade trade) {
        RuleValidationResult objectRuleCheck = objectRule.check(trade);
        if (objectRuleCheck.isSuccess())
            return TradeValidationResult.create(rules.stream()
                    .filter(rule -> rule.appliedFor(trade))
                    .filter(rule -> !(rule instanceof ObjectRule))
                    .map(rule -> rule.check(trade))
                    .filter(ruleValidationResult -> !ruleValidationResult.isSuccess())
                    .collect(Collectors.toList()));
        return TradeValidationResult.create(ImmutableList.of(objectRuleCheck));
    }


}
