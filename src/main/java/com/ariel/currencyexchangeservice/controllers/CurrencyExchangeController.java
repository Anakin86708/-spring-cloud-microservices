package com.ariel.currencyexchangeservice.controllers;

import com.ariel.currencyexchangeservice.models.CurrencyExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment env;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retriveExchangeValue(@PathVariable String from, @PathVariable String to) {
        CurrencyExchange currencyExchange = new CurrencyExchange(100L, from, to, BigDecimal.valueOf(5.46));
        String port = env.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
