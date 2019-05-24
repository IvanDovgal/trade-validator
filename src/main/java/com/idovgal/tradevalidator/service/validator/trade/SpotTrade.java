package com.idovgal.tradevalidator.service.validator.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

/**
 * @author ivan
 */
@JsonIgnoreProperties(value = {"type"}, allowGetters = true)
@ApiModel(parent = Trade.class)
public class SpotTrade extends BaseTrade {

    private final String type = SPOT_TYPE;

    public String getType() {
        return type;
    }
}