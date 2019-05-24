package com.idovgal.tradevalidator.service.calendar;

import java.time.LocalDate;

/**
 * Calendar service
 * @author ivan
 */
public interface CalendarService {

    /**
     * checking that given date is working date
     * @param currency currency for checking
     * @param date date for checking
     * @return flag that given date is working date
     */
    boolean checkWorkingDay(String currency, LocalDate date);

    /**
     * @return current date
     */
    LocalDate currentDate();

}
