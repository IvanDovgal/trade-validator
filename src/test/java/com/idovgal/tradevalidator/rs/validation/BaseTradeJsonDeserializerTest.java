package com.idovgal.tradevalidator.rs.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.idovgal.tradevalidator.service.validator.trade.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * @author ivan
 */
public class BaseTradeJsonDeserializerTest {

    private static String SPOT = "{\"type\": \"Spot\"}";
    private static String FORWARD = "{\"type\": \"Forward\"}";
    private static String AMERICAN_OPTION = "{\"type\": \"VanillaOption\", \"style\": \"AMERICAN\"}";
    private static String EUROPEAN_OPTION = "{\"type\": \"VanillaOption\", \"style\": \"EUROPEAN\"}";

    @Test
    public void deserialize() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Trade.class, new BaseTradeJsonDeserializer());
        objectMapper.registerModule(module);
        assertTrue(objectMapper.readValue(SPOT, Trade.class) instanceof SpotTrade);
        assertTrue(objectMapper.readValue(FORWARD, Trade.class) instanceof ForwardTrade);
        assertTrue(objectMapper.readValue(AMERICAN_OPTION, Trade.class) instanceof AmericanVanillaOptionTrade);
        assertTrue(objectMapper.readValue(EUROPEAN_OPTION, Trade.class) instanceof EuropeanVanillaOptionTrade);
    }
}