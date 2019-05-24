package com.idovgal.tradevalidator.service.validator.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

/**
 * @author ivan
 */
@JsonIgnoreProperties(value = {"type"}, allowGetters = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "style")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AmericanVanillaOptionTrade.class, name = "AMERICAN"),
        @JsonSubTypes.Type(value = EuropeanVanillaOptionTrade.class, name = "EUROPEAN"),
})
@ApiModel(
        parent = Trade.class,
        discriminator = "style",
        subTypes = {
                AmericanVanillaOptionTrade.class, EuropeanVanillaOptionTrade.class
        }
)
public abstract class VanillaOptionTrade extends Trade {

    @ApiModelProperty("Type for AmericanVanillaOptionTrade and EuropeanVanillaOptionTrade should be VanillaOptionTrade")
    private final String type = VANILLA_OPTION_TYPE;

    private LocalDate deliveryDate;
    private LocalDate expiryDate;
    private String payCcy;
    private String premiumCcy;
    private String premiumType;
    private LocalDate premiumDate;
    private Strategy strategy;

    @NotNull
    public String getType() {
        return type;
    }

    @NotNull
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @NotNull
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    @NotNull
    public String getPayCcy() {
        return payCcy;
    }

    public void setPayCcy(String payCcy) {
        this.payCcy = payCcy;
    }

    @NotNull
    public String getPremiumCcy() {
        return premiumCcy;
    }

    public void setPremiumCcy(String premiumCcy) {
        this.premiumCcy = premiumCcy;
    }

    @NotNull
    public String getPremiumType() {
        return premiumType;
    }

    public void setPremiumType(String premiumType) {
        this.premiumType = premiumType;
    }

    @NotNull
    public LocalDate getPremiumDate() {
        return premiumDate;
    }

    public void setPremiumDate(LocalDate premiumDate) {
        this.premiumDate = premiumDate;
    }

    @NotNull
    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VanillaOptionTrade that = (VanillaOptionTrade) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(deliveryDate, that.deliveryDate) &&
                Objects.equals(expiryDate, that.expiryDate) &&
                Objects.equals(payCcy, that.payCcy) &&
                Objects.equals(premiumCcy, that.premiumCcy) &&
                Objects.equals(premiumType, that.premiumType) &&
                Objects.equals(premiumDate, that.premiumDate) &&
                strategy == that.strategy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, deliveryDate, expiryDate, payCcy, premiumCcy, premiumType, premiumDate, strategy);
    }

    @Override
    public Map<String, String> getCurrencies() {
        return ImmutableMap.<String, String>builder()
                .putAll(super.getCurrencies())
                .put("payCcy", payCcy)
                .put("premiumCcy", premiumCcy)
                .build();
    }

    public enum Strategy {
        CALL
    }
}
