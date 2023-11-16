package com.dime.wadiag.diag.wordsapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WordsApiPropertiesTest {

    @Autowired
    private WordsApiProperties wordsApiProperties;

    @Test
    @DisplayName("Test wordsApi fill properties")
    void test_load_properties_file_then_all_fields_are_set() {
        assertThat(wordsApiProperties.getUrl()).isNotNull();
        assertThat(wordsApiProperties.getUrl()).startsWith("http");

        assertThat(wordsApiProperties.getKey()).isNotNull();
        assertThat(wordsApiProperties.getKey()).hasSize(50);
    }

    @Test
    @DisplayName("Test wordsApi fill cŒategory")
    void test_category_enum() {
        WordsApiProperties.Category everything = WordsApiProperties.Category.EVERYTHING;
        WordsApiProperties.Category synonyms = WordsApiProperties.Category.SYNONYMS;
        WordsApiProperties.Category examples = WordsApiProperties.Category.EXAMPLES;

        assertEquals("", everything.getName());
        assertEquals("synonyms", synonyms.getName());
        assertEquals("examples", examples.getName());
    }

}
