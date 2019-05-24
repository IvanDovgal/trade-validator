package com.idovgal.tradevalidator.service.validator.impl.rules;

import com.google.common.collect.ImmutableList;
import com.idovgal.tradevalidator.service.calendar.CalendarService;
import com.idovgal.tradevalidator.service.validator.RuleValidationResult;
import com.idovgal.tradevalidator.service.validator.trade.SpotTrade;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author ivan
 */
public class ValueDateRuleTest {

    @Test
    public void check() {
        CalendarService calendarService = mock(CalendarService.class);
        when(calendarService.checkWorkingDay(anyString(), eq(LocalDate.of(2019, 3, 19)))).thenReturn(true);
        when(calendarService.checkWorkingDay(anyString(), eq(LocalDate.of(2019, 3, 20)))).thenReturn(true);
        when(calendarService.checkWorkingDay(anyString(), eq(LocalDate.of(2019, 3, 21)))).thenReturn(true);
        when(calendarService.checkWorkingDay(anyString(), eq(LocalDate.of(2019, 3, 22)))).thenReturn(true);
        when(calendarService.checkWorkingDay(anyString(), eq(LocalDate.of(2019, 3, 23)))).thenReturn(false);
        when(calendarService.checkWorkingDay(anyString(), eq(LocalDate.of(2019, 3, 24)))).thenReturn(false);
        ValueDateRule valueDateRule = new ValueDateRule(calendarService);
        SpotTrade trade = new SpotTrade();
        trade.setCcyPair("RUBUSD");
        trade.setTradeDate(LocalDate.of(2019, 3, 21));
        trade.setValueDate(LocalDate.of(2019, 3, 22));
        assertTrue("All dates is valid", valueDateRule.check(trade).isSuccess());
        trade.setTradeDate(LocalDate.of(2019, 3, 21));
        trade.setValueDate(LocalDate.of(2019, 3, 23));
        RuleValidationResult check = valueDateRule.check(trade);
        assertFalse("non working day on 23.03.2019", check.isSuccess());
        assertEquals("Rule error count is 1", check.getErrors().size(), 1);
        assertThat("Rule error field is valueDate", check.getErrors().iterator().next().getFields(), is(ImmutableList.of("valueDate")));
        trade.setTradeDate(LocalDate.of(2019, 3, 19));
        trade.setValueDate(LocalDate.of(2019, 3, 22));
        check = valueDateRule.check(trade);
        assertFalse("Spot valueDate > tradeDate + 2DAY", check.isSuccess());
        assertEquals("Rule error count is 1", check.getErrors().size(), 1);
        assertThat("Rule error fields is valueDate and tradeDate", check.getErrors().iterator().next().getFields(), is(ImmutableList.of("valueDate", "tradeDate")));
    }

}