package com.idovgal.tradevalidator.service.validator.impl.rules;

import com.idovgal.tradevalidator.service.calendar.CalendarService;
import com.idovgal.tradevalidator.service.validator.Rule;
import com.idovgal.tradevalidator.service.validator.RuleDescription;
import com.idovgal.tradevalidator.service.validator.RuleValidationResult;
import com.idovgal.tradevalidator.service.validator.trade.BaseTrade;
import com.idovgal.tradevalidator.service.validator.trade.SpotTrade;
import com.idovgal.tradevalidator.service.validator.trade.Trade;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * @author ivan
 */
@Component
public class ValueDateRule implements Rule {

    final CalendarService calendarService;

    public ValueDateRule(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Override
    public boolean appliedFor(Trade trade) {
        return trade instanceof BaseTrade;
    }

    @Override
    public RuleDescription description() {
        return RuleDescription.create("value-date", "value date cannot be before trade date and cannot fall on weekend or non-working day for currency");
    }

    @Override
    public RuleValidationResult check(Trade trade) {
        BaseTrade baseTrade = (BaseTrade) trade;
        ArrayList<RuleValidationResult.ErrorDescription> errorsList = new ArrayList<>(2);
        LocalDate valueDate = baseTrade.getValueDate();
        LocalDate tradeDate = baseTrade.getTradeDate();
        if (valueDate.isBefore(tradeDate))
            errorsList.add(RuleValidationResult.errorDescription("Value date before trade date", "valueDate", "tradeDate"));
        String ccyPair = trade.getCcyPair();
        if (ccyPair.length() == 6) {
            String firstCurrency = ccyPair.substring(0, 3);
            String secondCurrency = ccyPair.substring(3, 6);
            if (!(calendarService.checkWorkingDay(firstCurrency, valueDate) && calendarService.checkWorkingDay(secondCurrency, valueDate))) {
                errorsList.add(RuleValidationResult.errorDescription("Value date cannot be weekend or holiday", "valueDate"));
            }
        }
        if (baseTrade instanceof SpotTrade && ChronoUnit.DAYS.between(tradeDate, valueDate) > 2) {
            errorsList.add(RuleValidationResult.errorDescription("Spot valueDate > tradeDate + 2DAY", "valueDate", "tradeDate"));
        }
        return errorsList.isEmpty() ? RuleValidationResult.ok(description()) : RuleValidationResult.fail(description(), errorsList);
    }
}
