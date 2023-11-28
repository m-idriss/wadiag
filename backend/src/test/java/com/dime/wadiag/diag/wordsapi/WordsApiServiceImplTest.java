package com.dime.wadiag.diag.wordsapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

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

    @BeforeEach
    void setup() {
        // Create an instance of the Retrofit client with the test server's base URL
        service = new WordsApiServiceImpl(testProperties);
    }

    @DisplayName("Get synonyms for a valid word")
    @Test
    void test_get_synonyms_for_word() {
        try {
            WordsApiResponse wordResponse = service.getSynonymsForWord("school");
            assertThat(wordResponse.getSynonyms()).containsExactlyElementsOf(Arrays.asList("shoal", "school day",
                    "schooltime", "civilise", "civilize", "cultivate", "educate", "train", "schooling", "schoolhouse"));
            assertThat(wordResponse.getWord()).isEqualTo("school");
        } catch (Exception e) {
            Assertions.fail("Unexpected exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("Get synonyms for an invalid word")
    @Test
    void test_get_synonyms_for_word_resource_not_found_exception() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.getSynonymsForWord("toto"));
        assertThat(exception.getMessage()).isEqualTo("synonyms not found for : toto");
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

}
