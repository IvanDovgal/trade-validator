package com.idovgal.tradevalidator.service.validator.impl.rules;

import com.idovgal.tradevalidator.service.validator.Rule;
import com.idovgal.tradevalidator.service.validator.RuleDescription;
import com.idovgal.tradevalidator.service.validator.RuleValidationResult;
import com.idovgal.tradevalidator.service.validator.trade.AmericanVanillaOptionTrade;
import com.idovgal.tradevalidator.service.validator.trade.Trade;
import com.idovgal.tradevalidator.service.validator.trade.VanillaOptionTrade;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ivan
 */
@Component
public class OptionRule implements Rule {

    @Override
    public RuleDescription description() {
        return RuleDescription.create("options", "options specific rule");
    }

    @Override
    public boolean appliedFor(Trade trade) {
        return trade instanceof VanillaOptionTrade;
    }

    @Override
    public RuleValidationResult check(Trade trade) {
        List<RuleValidationResult.ErrorDescription> errorsList = new ArrayList<>();
        if (trade instanceof AmericanVanillaOptionTrade) {
            AmericanVanillaOptionTrade americanVanillaOptionTrade = (AmericanVanillaOptionTrade) trade;
            LocalDate excerciseStartDate = americanVanillaOptionTrade.getExcerciseStartDate();
            LocalDate expiryDate = americanVanillaOptionTrade.getExpiryDate();
            LocalDate tradeDate = americanVanillaOptionTrade.getTradeDate();
            if (!(excerciseStartDate.isAfter(tradeDate) && excerciseStartDate.isBefore(expiryDate))) {
                errorsList.add(RuleValidationResult.errorDescription(
                        "American option style will have in addition the excerciseStartDate, which has to be after the trade date but before the expiry date",
                        "excerciseStartDate",
                        "expiryDate",
                        "tradeDate"
                        )
                );
            }
        }
        VanillaOptionTrade optionTrade = (VanillaOptionTrade) trade;
        LocalDate expiryDate = optionTrade.getExpiryDate();
        LocalDate premiumDate = optionTrade.getPremiumDate();
        LocalDate deliveryDate = optionTrade.getDeliveryDate();
        if (!(expiryDate.isBefore(deliveryDate) && premiumDate.isBefore(deliveryDate))) {
            errorsList.add(RuleValidationResult.errorDescription(
                    "expiry date and premium date shall be before delivery date",
                    "expiryDate",
                    "premiumDate",
                    "deliveryDate"
                    )
            );
        }
        return errorsList.isEmpty() ? RuleValidationResult.ok(description()) : RuleValidationResult.fail(description(), errorsList);
    }
}
