package com.dime.wadiag.diag.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.dime.wadiag.diag.exception.GenericException;
import com.dime.wadiag.diag.model.Term;
import com.dime.wadiag.diag.wordsapi.WordsApiProperties;
import com.dime.wadiag.kafka.KafkaPublisher;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration
class WordsApiServiceImplTest {

    @Autowired
    WordsApiServiceImpl service;

    @Autowired
    WordsApiProperties testProperties;

    @Autowired
    KafkaPublisher kafkaPublisher;

    @BeforeEach
    void setup() {
        service = new WordsApiServiceImpl(testProperties, kafkaPublisher);
    }

    @DisplayName("Get synonyms for a valid word")
    @Test
    void test_get_synonyms_for_word() {
        try {
            Term termResponse = service.getSynonymsForWord("school");
            assertThat(termResponse.getSynonyms()).containsExactlyElementsOf(Arrays.asList("shoal", "school day",
                    "schooltime", "civilise", "civilize", "cultivate", "educate", "train", "schooling", "schoolhouse"));
            assertThat(termResponse.getWord()).isEqualTo("school");
        } catch (Exception e) {
            Assertions.fail("Unexpected exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("Get synonyms for an invalid word")
    @Test
    void test_get_synonyms_for_word_resource_not_found_exception() {
        GenericException exception = assertThrows(GenericException.class,
                () -> service.getSynonymsForWord("toto"));
        assertThat(exception.getMessage()).isEqualTo("Word [toto] do not exists");
    }

    @DisplayName("test request to WordsAPI and receives a successful response")
    @Test
    void test_successful_response() throws IOException {
        WordsApiServiceImpl wordsApiService = Mockito.mock(WordsApiServiceImpl.class);
        Mockito.when(wordsApiService.testWordsApiConnection()).thenReturn(true);

        boolean result = wordsApiService.testWordsApiConnection();

        assertTrue(result);
    }

    @DisplayName("Throws an IOException when the response is not successful")
    @Test
    void test_unsuccessful_response() throws IOException {
        WordsApiServiceImpl wordsApiService = Mockito.mock(WordsApiServiceImpl.class);
        Mockito.when(wordsApiService.testWordsApiConnection()).thenThrow(new IOException());

        assertThrows(IOException.class, () -> wordsApiService.testWordsApiConnection());
    }

    @DisplayName("The WordsAPI server is down, and the method throws an IOException with an appropriate error message")
    @Test
    void test_server_down() throws IOException {
        WordsApiServiceImpl wordsApiService = Mockito.mock(WordsApiServiceImpl.class);
        Mockito.when(wordsApiService.testWordsApiConnection())
                .thenThrow(new IOException("Failed to connect to WordsAPI"));

        IOException exception = assertThrows(IOException.class, () -> wordsApiService.testWordsApiConnection());
        assertEquals("Failed to connect to WordsAPI", exception.getMessage());
    }

    @Test
    void test_successful_request() throws IOException {
        assertTrue(service.testWordsApiConnection());
    }

    @Test
    void test_bad_request() throws IOException {
        WordsApiProperties properties = new WordsApiProperties();
        properties.setKey("key");
        properties.setUrl(testProperties.getUrl());
        service = new WordsApiServiceImpl(properties, kafkaPublisher);

        GenericException exception = assertThrows(GenericException.class,
                () -> service.testWordsApiConnection());
        assert (exception.getMessage()).contains("Failed to connect to dependency code : [");
    }

}
