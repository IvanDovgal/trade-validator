package com.idovgal.tradevalidator.service.validator.trade;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author ivan
 */
public abstract class BaseTrade extends Trade {

    private LocalDate valueDate;

    @NotNull
    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseTrade baseTrade = (BaseTrade) o;
        return Objects.equals(valueDate, baseTrade.valueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), valueDate);
    }
}
