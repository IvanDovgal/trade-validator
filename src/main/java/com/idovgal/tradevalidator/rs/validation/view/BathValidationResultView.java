package com.idovgal.tradevalidator.rs.validation.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.idovgal.tradevalidator.service.validator.RuleDescription;
import com.idovgal.tradevalidator.service.validator.RuleValidationResult;
import com.idovgal.tradevalidator.service.validator.TradeValidationResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * View for results of batch validation
 *
 * @author ivan
 */
public class BathValidationResultView {

    private final List<IndexedValidationResultView> items;

    @JsonCreator
    public BathValidationResultView(List<IndexedValidationResultView> items) {
        this.items = items;
    }

    public static BathValidationResultView of(List<TradeValidationResult> results) {
        List<IndexedValidationResultView> validationResultViews = IntStream.range(0, results.size())
                .mapToObj(index -> IndexedValidationResultView.of(index, results.get(index)))
                .filter(indexedValidationResultView -> !indexedValidationResultView.getRules().isEmpty())
                .collect(Collectors.toList());
        return new BathValidationResultView(validationResultViews);
    }

    @JsonValue
    public List<IndexedValidationResultView> getItems() {
        return items;
    }

    public static class IndexedValidationResultView extends ValidationResultView {

        private final int index;

        public IndexedValidationResultView(int index, List<RuleDescription> rules, List<RuleValidationResult.ErrorDescription> errors) {
            super(rules, errors);
            this.index = index;
        }

        public static IndexedValidationResultView of(int index, TradeValidationResult tradeValidationResult) {
            return new IndexedValidationResultView(index, getRuleDescriptions(tradeValidationResult), getErrorDescriptions(tradeValidationResult));
        }

        public int getIndex() {
            return index;
        }
    }

}
