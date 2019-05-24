package com.idovgal.tradevalidator.service.validator.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author ivan
 */
@JsonIgnoreProperties(value = {"type"}, allowGetters = true)
public class ForwardTrade extends BaseTrade {

    private final String type = FORWARD_TYPE;

    public String getType() {
        return type;
    }

}




