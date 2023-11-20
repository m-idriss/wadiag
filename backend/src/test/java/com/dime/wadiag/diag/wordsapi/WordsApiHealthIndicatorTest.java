package com.dime.wadiag.diag.wordsapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
class WordsApiHealthIndicatorTest {

    @Test
    void testHealthyConnection() throws IOException {
        WordsApiService wordsApiService = Mockito.mock(WordsApiService.class);
        Mockito.when(wordsApiService.testWordsApiConnection()).thenReturn(true);

        WordsApiHealthIndicator healthIndicator = new WordsApiHealthIndicator(wordsApiService);
        Health health = healthIndicator.health();

        assertEquals(Status.UP, health.getStatus());
    }

    @Test
    void testUnhealthyConnection() throws IOException {
        WordsApiService wordsApiService = Mockito.mock(WordsApiService.class);
        Mockito.when(wordsApiService.testWordsApiConnection()).thenThrow(new RuntimeException("Connection error"));

        WordsApiHealthIndicator healthIndicator = new WordsApiHealthIndicator(wordsApiService);
        Health health = healthIndicator.health();

        assertEquals(Status.DOWN, health.getStatus());
        assertEquals("Connection error", health.getDetails().get("error"));
    }
}
