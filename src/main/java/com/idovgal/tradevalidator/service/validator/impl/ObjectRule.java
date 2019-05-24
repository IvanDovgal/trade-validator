package com.idovgal.tradevalidator.service.validator.impl;

import com.idovgal.tradevalidator.service.validator.Rule;
import com.idovgal.tradevalidator.service.validator.RuleDescription;
import com.idovgal.tradevalidator.service.validator.RuleValidationResult;
import com.idovgal.tradevalidator.service.validator.trade.Trade;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Bridge for jsr380
 *
 * @author ivan
 */
@Component
public class ObjectRule implements Rule {

    final Validator validator;

    public ObjectRule(Validator validator) {
        this.validator = validator;
    }

    @Override
    public RuleDescription description() {
        return RuleDescription.create("common", "common validation");
    }

    @Override
    public boolean appliedFor(Trade trade) {
        return true;
    }

    @Override
    public RuleValidationResult check(Trade trade) {
        Set<ConstraintViolation<Trade>> validate = validator.validate(trade);
        if (validate.isEmpty())
            return RuleValidationResult.ok(description());
        return RuleValidationResult.fail(description(),
                validate.stream()
                        .map(tradeConstraintViolation -> RuleValidationResult.errorDescription(tradeConstraintViolation.getMessage(),
                                tradeConstraintViolation.getPropertyPath().toString()))
                        .collect(Collectors.toList())
        );
    }
}
