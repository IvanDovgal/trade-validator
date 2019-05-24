package com.idovgal.tradevalidator.service.calendar.impl;

import com.idovgal.tradevalidator.domain.Holiday;
import com.idovgal.tradevalidator.repository.HolidayRepository;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author ivan
 */
public class CalendarServiceImplTest {

    @Test
    public void checkWorkingDay() {
        HolidayRepository holidayRepository = mock(HolidayRepository.class);
        when(holidayRepository.findByCurrencyAndDate(anyString(), any())).thenReturn(Optional.empty());
        when(holidayRepository.findByCurrencyAndDate("RUB", LocalDate.of(2019, 1, 1))).thenReturn(Optional.of(new Holiday(LocalDate.of(2019, 1, 1), "RUB", false)));
        CalendarServiceImpl calendarService = new CalendarServiceImpl(holidayRepository);
        assertFalse("Sunday is not working day in Russia", calendarService.checkWorkingDay("RUB", LocalDate.of(2019, 1, 13)));
        assertTrue("Sunday is working day in ILS", calendarService.checkWorkingDay("ILS", LocalDate.of(2019, 1, 13)));
        assertFalse("New Year is holiday in Russia", calendarService.checkWorkingDay("RUB", LocalDate.of(2019, 1, 1)));
        assertTrue("Tuesday is working day", calendarService.checkWorkingDay("RUB", LocalDate.of(2019, 2, 5)));
    }
}