package com.ariel.currencyexchangeservice.controllers;

import com.ariel.currencyexchangeservice.models.CurrencyExchange;
import com.ariel.currencyexchangeservice.repositories.CurrencyExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CurrencyExchangeController {

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @Autowired
    private CurrencyExchangeRepository repository;

    @Autowired
    private Environment env;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retriveExchangeValue(@PathVariable String from, @PathVariable String to) {
        CurrencyExchange currencyExchange = getValidCurrencyExchange(from.toUpperCase(), to.toUpperCase());
        String port = env.getProperty("local.server.port");
        String host = env.getProperty("HOSTNAME");
        String version = "v1";
        currencyExchange.setEnvironment(port + " " + version + " " + host);

        logger.info(String.format("retrieveExchange called with %s and %s", from, to));

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
