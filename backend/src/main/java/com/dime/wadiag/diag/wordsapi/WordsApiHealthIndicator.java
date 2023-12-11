package com.dime.wadiag.diag.wordsapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.dime.wadiag.diag.service.impl.WordsApiServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WordsApiHealthIndicator implements HealthIndicator {

    @Autowired
    private final WordsApiServiceImpl wordsApiService;

    public WordsApiHealthIndicator(WordsApiServiceImpl wordsApiService) {
        this.wordsApiService = wordsApiService;
    }

    @Override
    public Health health() {
        try {
            log.info("Performing Words API connection test...");
            wordsApiService.testWordsApiConnection();
            log.info("Words API connection test successful.");
            return Health.up().build();
        } catch (Exception e) {
            log.error("Unexpected error during Words API connection test", e);
            return Health.down().withDetail("error", e.getMessage()).build();
        }
    }
}
