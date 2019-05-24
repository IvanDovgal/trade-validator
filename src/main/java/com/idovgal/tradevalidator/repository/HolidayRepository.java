package com.idovgal.tradevalidator.repository;

import com.idovgal.tradevalidator.domain.Holiday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for store holidays in countries(currencies)
 *
 * @author ivan
 */
@Repository
public interface HolidayRepository extends CrudRepository<Holiday, UUID> {

    /**
     * find holiday entity for given currency and date
     *
     * @param currency currency for search
     * @param date     date for search
     * @return optional holiday for given arguments
     */
    Optional<Holiday> findByCurrencyAndDate(String currency, LocalDate date);

}
