package com.idovgal.tradevalidator.service.validator;

/**
 * Rule description
 * @author ivan
 */
public class RuleDescription {

    private final String name;
    private final String description;

    public RuleDescription(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static RuleDescription create(String name, String description) {
        return new RuleDescription(name, description);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


}
