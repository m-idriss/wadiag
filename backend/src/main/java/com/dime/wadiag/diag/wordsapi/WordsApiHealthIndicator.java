package com.dime.wadiag.diag.wordsapi;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class WordsApiHealthIndicator implements HealthIndicator {

    private final WordsApiService wordsApiService;

    public WordsApiHealthIndicator(WordsApiService wordsApiService) {
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
