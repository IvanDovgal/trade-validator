package com.idovgal.tradevalidator.service.validator.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.SwaggerDefinition;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * @author ivan
 */
@ApiModel(
        discriminator = "type",
        subTypes = {
                SpotTrade.class, ForwardTrade.class,
                VanillaOptionTrade.class
        }
)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AmericanVanillaOptionTrade.class, name = "VanillaOption"),
        @JsonSubTypes.Type(value = EuropeanVanillaOptionTrade.class, name = "VanillaOption"),
        @JsonSubTypes.Type(value = SpotTrade.class, name = "Spot"),
        @JsonSubTypes.Type(value = ForwardTrade.class, name = "Forward")
})
public abstract class Trade {

    public static final String SPOT_TYPE = "Spot";
    public static final String FORWARD_TYPE = "Forward";
    public static final String VANILLA_OPTION_TYPE = "VanillaOption";
    public static final String AMERICAN_STYLE = "AMERICAN";
    public static final String EUROPEAN_STYLE = "EUROPEAN";
    private String customer;
    private String trader;
    private String ccyPair;
    private String legalEntity;
    private LocalDate tradeDate;
    private BigDecimal amount1;
    private BigDecimal amount2;
    private BigDecimal rate;
    private Direction direction;

    @NotNull
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @NotNull
    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    @NotNull
    @Pattern(regexp = "^[A-Z]{6}$")
    public String getCcyPair() {
        return ccyPair;
    }

    public void setCcyPair(String ccyPair) {
        this.ccyPair = ccyPair;
    }

    @NotNull
    @Pattern(regexp = "^CS Zurich$")
    public String getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    @NotNull
    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    @NotNull
    public BigDecimal getAmount1() {
        return amount1;
    }

    public void setAmount1(BigDecimal amount1) {
        this.amount1 = amount1;
    }

    @NotNull
    public BigDecimal getAmount2() {
        return amount2;
    }

    public void setAmount2(BigDecimal amount2) {
        this.amount2 = amount2;
    }

    @NotNull
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @NotNull
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @JsonIgnore
    public Map<String, String> getCurrencies() {
        return ImmutableMap.of();
    }

    @JsonIgnore
    public Map<String, String> getCurrencyPairs() {
        return ImmutableMap.of("ccyPair", ccyPair);
    }

    public enum Direction {
        SELL,
        BUY
    }
}
