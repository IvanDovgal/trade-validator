package com.idovgal.tradevalidator.service.validator.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpotTrade spotTrade = (SpotTrade) o;
        return Objects.equals(type, spotTrade.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
    }
}