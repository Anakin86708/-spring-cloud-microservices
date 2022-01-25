package com.ariel.currencyexchangeservice.controllers;

import com.ariel.currencyexchangeservice.models.CurrencyExchange;
import com.ariel.currencyexchangeservice.repositories.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeRepository repository;

    @Autowired
    private Environment env;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retriveExchangeValue(@PathVariable String from, @PathVariable String to) {
        CurrencyExchange currencyExchange = getValidCurrencyExchange(from.toUpperCase(), to.toUpperCase());
        String port = env.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }

    private CurrencyExchange getValidCurrencyExchange(String from, String to) {
        Optional<CurrencyExchange> currencyExchangeOpt = repository.findByFromAndTo(from, to);
        if (currencyExchangeOpt.isEmpty()) {
            throw new RuntimeException("Invalid currency");  // TODO: implementar futuramente uma exceção apropriada
        }
        return currencyExchangeOpt.get();
    }
}
