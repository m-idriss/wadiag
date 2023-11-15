package com.dime.wadiag.diag.wordsapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WordsApiPropertiesTest {

    @Autowired
    private WordsApiProperties wordsApiProperties;

    @Test
    void testPropertiesLoading() {
        assertNotNull(wordsApiProperties.getKey());
    }

    @Test
    void testCategoryEnum() {
        WordsApiProperties.Category everything = WordsApiProperties.Category.EVERYTHING;
        WordsApiProperties.Category synonyms = WordsApiProperties.Category.SYNONYMS;
        WordsApiProperties.Category examples = WordsApiProperties.Category.EXAMPLES;

        assertEquals("", everything.getName());
        assertEquals("synonyms", synonyms.getName());
        assertEquals("examples", examples.getName());
    }
}
