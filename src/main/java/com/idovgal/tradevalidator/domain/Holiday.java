package com.idovgal.tradevalidator.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Holiday entity
 * @author ivan
 */
@Entity
public class Holiday {
    private UUID id;
    private LocalDate date;
    private String currency;
    private boolean workingDay;

    public Holiday() {

    }

    public Holiday(LocalDate date, String currency, boolean workingDay) {
        this.date = date;
        this.currency = currency;
        this.workingDay = workingDay;
    }

    public Holiday(UUID id, LocalDate date, String currency, boolean workingDay) {
        this.id = id;
        this.date = date;
        this.currency = currency;
        this.workingDay = workingDay;
    }

    @Id
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    /**
     * @return date of holiday
     */
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return currency(as relation country) for holiday day
     */
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return if day fall on the weekend, this flag determine that this weekend is working day
     */
    public boolean isWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(boolean workingDay) {
        this.workingDay = workingDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holiday holiday = (Holiday) o;
        return workingDay == holiday.workingDay &&
                Objects.equals(id, holiday.id) &&
                Objects.equals(date, holiday.date) &&
                Objects.equals(currency, holiday.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, currency, workingDay);
    }
}
