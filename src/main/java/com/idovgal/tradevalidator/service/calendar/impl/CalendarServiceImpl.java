package com.idovgal.tradevalidator.service.calendar.impl;

import com.google.common.collect.ImmutableSet;
import com.idovgal.tradevalidator.domain.Holiday;
import com.idovgal.tradevalidator.repository.HolidayRepository;
import com.idovgal.tradevalidator.service.calendar.CalendarService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

/**
 * @author ivan
 */
@Component
public class CalendarServiceImpl implements CalendarService {


    final HolidayRepository holidayRepository;
    private final LocalDate currentDate = LocalDate.of(2017, 7, 18);

    public CalendarServiceImpl(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    /**
     * @param currency currency for retrieve weekends days of week
     * @return set of weekend days of week
     */
    private Set<DayOfWeek> getWeekends(String currency) {
        //todo store that in db/filesystem/etc
        switch (currency) {
            case "CNY":
                return ImmutableSet.of(DayOfWeek.SUNDAY);
            case "AED":
                return ImmutableSet.of(DayOfWeek.FRIDAY);
            case "ILS":
                return ImmutableSet.of(DayOfWeek.SATURDAY);
            default:
                return ImmutableSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        }
    }

    @Override
    @Cacheable(cacheNames = "working-day")
    public boolean checkWorkingDay(String currency, LocalDate date) {
        Optional<Holiday> holidayOptional = holidayRepository.findByCurrencyAndDate(currency, date);
        if (holidayOptional.isPresent()) {
            Holiday holiday = holidayOptional.get();
            return holiday.isWorkingDay();
        }
        return !getWeekends(currency).contains(date.getDayOfWeek());
    }

    @Override
    public LocalDate currentDate() {
        return this.currentDate;
    }


}
