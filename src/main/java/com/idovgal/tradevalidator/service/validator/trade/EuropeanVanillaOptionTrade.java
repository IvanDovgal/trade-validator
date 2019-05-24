package com.idovgal.tradevalidator.service.validator.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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

}