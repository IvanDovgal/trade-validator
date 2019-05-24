package com.idovgal.tradevalidator.service.validator.trade;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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
}
