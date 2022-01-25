package com.ariel.currencyconversionservice.controllers;

import com.ariel.currencyconversionservice.models.CurrencyConversion;
import com.ariel.currencyconversionservice.proxies.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy exchangeProxy;

    @GetMapping("currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion retrieveCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        CurrencyConversion currencyConversion = exchangeProxy.retrieveExchangeValue(from, to);
        BigDecimal conversionMultiple = currencyConversion.getConversionMultiple();
        return new CurrencyConversion(currencyConversion.getId(), from, to, conversionMultiple, quantity, calculate(quantity, conversionMultiple), currencyConversion.getEnvironment());
    }

    private BigDecimal calculate(BigDecimal quantity, BigDecimal conversionMultiple) {
        return quantity.multiply(conversionMultiple);
    }
}
