package com.dime.wadiag.diag.wordsapi;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.dime.wadiag.diag.service.impl.WordsApiServiceImpl;

@Component
public class WordsApiHealthIndicator implements HealthIndicator {

    private final WordsApiServiceImpl wordsApiService;

    public WordsApiHealthIndicator(WordsApiServiceImpl wordsApiService) {
        this.wordsApiService = wordsApiService;
    }

    @Override
    public Health health() {
        try {
            wordsApiService.testWordsApiConnection();
            return Health.up().build();
        } catch (Exception e) {
            return Health.down().withDetail("error", e.getMessage()).build();
        }
    }
}
