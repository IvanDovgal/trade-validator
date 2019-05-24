package com.idovgal.tradevalidator.service.validator.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @author ivan
 */
@JsonIgnoreProperties(value = {"style"}, allowGetters = true)
@ApiModel(parent = VanillaOptionTrade.class)
public class EuropeanVanillaOptionTrade extends VanillaOptionTrade {

    @ApiModelProperty("Style for EuropeanVanillaOptionTrade should be EUROPEAN")
    private final String style = EUROPEAN_STYLE;

    public String getStyle() {
        return style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EuropeanVanillaOptionTrade that = (EuropeanVanillaOptionTrade) o;
        return Objects.equals(style, that.style);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), style);
    }
}