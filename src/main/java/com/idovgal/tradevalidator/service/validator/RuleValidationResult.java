package com.idovgal.tradevalidator.service.validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Rule validation result
 * @author ivan
 */
public class RuleValidationResult {

    public final boolean success;
    public final List<ErrorDescription> errors;
    public final RuleDescription rule;

    public RuleValidationResult(RuleDescription rule, boolean success, List<ErrorDescription> errors) {
        this.success = success;
        this.errors = errors;
        this.rule = rule;
    }

    public static ErrorDescription errorDescription(String msg, List<String> fields) {
        return new ErrorDescription(msg, fields);
    }

    public static ErrorDescription errorDescription(String msg, String... fields) {
        return new ErrorDescription(msg, Arrays.asList(fields));
    }

    public static RuleValidationResult create(RuleDescription rule, boolean success, List<ErrorDescription> errors) {
        return new RuleValidationResult(rule, success, errors);
    }

    public static RuleValidationResult fail(RuleDescription rule, List<ErrorDescription> errors) {
        return new RuleValidationResult(rule, false, errors);
    }

    public static RuleValidationResult fail(RuleDescription rule, ErrorDescription... errors) {
        return new RuleValidationResult(rule, false, Arrays.asList(errors));
    }

    public static RuleValidationResult ok(RuleDescription rule) {
        return new RuleValidationResult(rule, true, Collections.emptyList());
    }

    /**
     * @return True if rule validation success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @return List of errors
     */
    public List<ErrorDescription> getErrors() {
        return errors;
    }

    /**
     * @return Rule description
     */
    public RuleDescription getRule() {
        return rule;
    }

    /**
     * Error description
     * @author ivan
     */
    public static class ErrorDescription {

        public final List<String> fields;
        public final String msg;

        public ErrorDescription(String msg, List<String> fields) {
            this.fields = fields;
            this.msg = msg;
        }

        /**
         * @return fields in the trade, which contains error
         */
        public List<String> getFields() {
            return fields;
        }

        /**
         * @return Text description of error
         */
        public String getMsg() {
            return msg;
        }
    }

}
