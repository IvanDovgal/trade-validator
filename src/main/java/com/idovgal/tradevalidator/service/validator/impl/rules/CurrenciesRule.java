package com.idovgal.tradevalidator.service.validator.impl.rules;

import com.google.common.collect.ImmutableList;
import com.idovgal.tradevalidator.service.validator.Rule;
import com.idovgal.tradevalidator.service.validator.RuleDescription;
import com.idovgal.tradevalidator.service.validator.RuleValidationResult;
import com.idovgal.tradevalidator.service.validator.trade.Trade;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author ivan
 */
@Component
public class CurrenciesRule implements Rule {

    private static final Set<String> currencyCodes = Currency.getAvailableCurrencies()
            .stream()
            .map(Currency::getCurrencyCode)
            .collect(Collectors.toSet());

    @Override
    public boolean appliedFor(Trade trade) {
        return true;
    }

    @Override
    public RuleDescription description() {
        return RuleDescription.create(
                "currencies",
                "validate currencies if they are valid ISO codes (ISO 4217)"
        );
    }

    List<RuleValidationResult.ErrorDescription> validateCurrencies(Map<String, String> currenciesFields) {
        return currenciesFields
                .entrySet()
                .stream()
                .filter(fieldValueEntry -> !currencyCodes.contains(fieldValueEntry.getValue()))
                .map(fieldValueEntry -> RuleValidationResult.errorDescription(
                        format("Currency code %s is not valid ISO 4217 code", fieldValueEntry.getValue()),
                        fieldValueEntry.getKey()
                        )
                )
                .collect(Collectors.toList());
    }


    private boolean validateCurrencyPair(String currencyPair) {
        if (currencyPair.length() != 6)
            return false;
        String firstCurrency = currencyPair.substring(0, 3);
        String secondCurrency = currencyPair.substring(3, 6);
        return currencyCodes.contains(firstCurrency) && currencyCodes.contains(secondCurrency);
    }

    List<RuleValidationResult.ErrorDescription> validateCurrencyPairs(Map<String, String> currencyPairsFields) {
        return currencyPairsFields
                .entrySet()
                .stream()
                .filter(fieldValueEntry -> !validateCurrencyPair(fieldValueEntry.getValue()))
                .map(fieldValueEntry -> RuleValidationResult.errorDescription(
                        format("Currency pair %s contains not valid ISO 4217 code", fieldValueEntry.getValue()),
                        fieldValueEntry.getKey()
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public RuleValidationResult check(Trade trade) {
        List<RuleValidationResult.ErrorDescription> errorsLists = ImmutableList.<RuleValidationResult.ErrorDescription>builder()
                .addAll(validateCurrencies(trade.getCurrencies()))
                .addAll(validateCurrencyPairs(trade.getCurrencyPairs()))
                .build();
        return errorsLists.isEmpty() ? RuleValidationResult.ok(description()) : RuleValidationResult.fail(description(), errorsLists);
    }
}
