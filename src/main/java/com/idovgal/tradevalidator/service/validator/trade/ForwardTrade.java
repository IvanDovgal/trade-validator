package com.idovgal.tradevalidator.service.validator.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

/**
 * @author ivan
 */
@JsonIgnoreProperties(value = {"type"}, allowGetters = true)
public class ForwardTrade extends BaseTrade {

    private final String type = FORWARD_TYPE;

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ForwardTrade that = (ForwardTrade) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
    }
}




