package com.ariel.currencyconversionservice.controllers;

import com.ariel.currencyconversionservice.models.CurrencyConversion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {
    @GetMapping("currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        CurrencyConversion currencyConversion = getCurrencyConversion(from, to);
        BigDecimal conversionMultiple = currencyConversion.getConversionMultiple();
        return new CurrencyConversion(currencyConversion.getId(), from, to, conversionMultiple, quantity, calculate(quantity, conversionMultiple), currencyConversion.getEnvironment());
    }

    private CurrencyConversion getCurrencyConversion(String from, String to) {
        String url = String.format("http://localhost:8000/currency-exchange/from/%s/to/%s/", from, to);
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversion> response = new RestTemplate().getForEntity(url, CurrencyConversion.class, uriVariables);
        return response.getBody();
    }

    private BigDecimal calculate(BigDecimal quantity, BigDecimal conversionMultiple) {
        return quantity.multiply(conversionMultiple);
    }
}
