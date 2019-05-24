package com.idovgal.tradevalidator.rs.validation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.idovgal.tradevalidator.service.validator.trade.*;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * Polymorphic deserializer for trade
 *
 * @author ivan
 */
@JsonComponent
public class BaseTradeJsonDeserializer extends JsonDeserializer<Trade> {

    @Override
    public Trade deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode node = jsonParser.readValueAsTree();
        String type = ((TextNode) node.get("type")).asText();
        switch (type) {
            case Trade.SPOT_TYPE:
                return jsonParser.getCodec().treeToValue(node, SpotTrade.class);
            case Trade.FORWARD_TYPE:
                return jsonParser.getCodec().treeToValue(node, ForwardTrade.class);
            case Trade.VANILLA_OPTION_TYPE:
                String style = ((TextNode) node.get("style")).asText();
                switch (style) {
                    case Trade.AMERICAN_STYLE:
                        return jsonParser.getCodec().treeToValue(node, AmericanVanillaOptionTrade.class);
                    case Trade.EUROPEAN_STYLE:
                        return jsonParser.getCodec().treeToValue(node, EuropeanVanillaOptionTrade.class);
                    default:
                        throw deserializationContext.instantiationException(VanillaOptionTrade.class, "Unrecognized trade style, style must be EUROPEAN or American");
                }
            default:
                throw deserializationContext.instantiationException(Trade.class, "Unrecognized trade type, type must be Spot, Forward or VanillaOption");
        }
    }
}