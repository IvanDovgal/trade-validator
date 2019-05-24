package com.idovgal.tradevalidator.service.validator.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

/**
 * @author ivan
 */
@JsonIgnoreProperties(value = {"style"}, allowGetters = true)
@ApiModel(parent = VanillaOptionTrade.class)
public class AmericanVanillaOptionTrade extends VanillaOptionTrade {

    @ApiModelProperty("Style for AmericanVanillaOptionTrade should be AMERICAN")
    private final String style = AMERICAN_STYLE;

    private LocalDate excerciseStartDate;

    public String getStyle() {
        return style;
    }

    public LocalDate getExcerciseStartDate() {
        return excerciseStartDate;
    }

    public void setExcerciseStartDate(LocalDate excerciseStartDate) {
        this.excerciseStartDate = excerciseStartDate;
    }

}