package com.idovgal.tradevalidator;

import com.idovgal.tradevalidator.rs.validation.view.BathValidationResultView;
import com.idovgal.tradevalidator.rs.validation.view.ValidationResultView;
import org.assertj.core.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author ivan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebTest {

    String trade1 = "{\n" +
            "  \"customer\": \"PLUTO1\",\n" +
            "  \"ccyPair\": \"EURUSD\",\n" +
            "  \"type\": \"Spot\",\n" +
            "  \"direction\": \"BUY\",\n" +
            "  \"tradeDate\": \"2016-08-11\",\n" +
            "  \"amount1\": 1000000.00,\n" +
            "  \"amount2\": 1120000.00,\n" +
            "  \"rate\": 1.12,\n" +
            "  \"valueDate\": \"2016-08-15\",\n" +
            "  \"legalEntity\": \"CS Zurich\",\n" +
            "  \"trader\": \"JohannBaumfiddler\"\n" +
            "}";
    String trade2 = "{\n" +
            "  \"customer\": \"PLUTO1\",\n" +
            "  \"ccyPair\": \"EURUSD\",\n" +
            "  \"type\": \"VanillaOption\",\n" +
            "  \"style\": \"EUROPEAN\",\n" +
            "  \"direction\": \"BUY\",\n" +
            "  \"strategy\": \"CALL\",\n" +
            "  \"tradeDate\": \"2016-08-11\",\n" +
            "  \"amount1\": 1000000.00,\n" +
            "  \"amount2\": 1120000.00,\n" +
            "  \"rate\": 1.12,\n" +
            "  \"deliveryDate\": \"2016-08-22\",\n" +
            "  \"expiryDate\": \"2016-08-19\",\n" +
            "  \"payCcy\": \"USD\",\n" +
            "  \"premium\": 0.20,\n" +
            "  \"premiumCcy\": \"USD\",\n" +
            "  \"premiumType\": \"%USD\",\n" +
            "  \"premiumDate\": \"2016-08-12\",\n" +
            "  \"legalEntity\": \"CS Zurich\",\n" +
            "  \"trader\": \"Johann Baumfiddler\"\n" +
            "}";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSingleTradeValidation() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(trade1, headers);
        ValidationResultView validationResultView = restTemplate.postForObject("http://localhost:" + port + "/trade/validation", request, ValidationResultView.class);
        assertEquals(validationResultView.getErrors().size(), 1);
        assertEquals(validationResultView.getRules().get(0).getName(), "value-date");
    }

    @Test
    public void testBatchTradeValidation() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(Strings.join("[", trade1, ", ", trade2, "]").with(""), headers);
        BathValidationResultView validationResultView = restTemplate.postForObject("http://localhost:" + port + "/trade/validation:batch", request, BathValidationResultView.class);
        assertEquals(validationResultView.getItems().size(), 1);
        assertEquals(validationResultView.getItems().get(0).getIndex(), 0);
        assertEquals(validationResultView.getItems().get(0).getRules().get(0).getName(), "value-date");
    }

}
