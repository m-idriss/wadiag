package com.dime.wadiag.diag.wordsapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    @DisplayName("Get synonyms for a valid word")
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

    @Test
    @DisplayName("Get synonyms for an invalid word")
    void test_get_synonyms_for_word_resource_not_found_exception() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.getSynonymsForWord("toto"));
        assertThat(exception.getMessage()).isEqualTo("synonyms not found for : toto");
    }

}
