package com.idovgal.tradevalidator.rs.validation;


import com.idovgal.tradevalidator.rs.validation.view.BathValidationResultView;
import com.idovgal.tradevalidator.rs.validation.view.ValidationResultView;
import com.idovgal.tradevalidator.service.validator.TradeValidator;
import com.idovgal.tradevalidator.service.validator.trade.Trade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for trade validation
 * @author ivan
 */
@RequestMapping(path = "/trade")
@RestController
public class TradeValidationController {


    private final TradeValidator tradeValidator;

    public TradeValidationController(TradeValidator tradeValidator) {
        this.tradeValidator = tradeValidator;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/validation")
    public ValidationResultView doSingleValidate(@RequestBody Trade trade) {
        return ValidationResultView.of(tradeValidator.check(trade));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/validation:batch")
    public BathValidationResultView doBatchValidate(@RequestBody List<Trade> trade) {
        return BathValidationResultView.of(
                trade.stream()
                        .map(tradeValidator::check)
                        .collect(Collectors.toList())
        );
    }

}
