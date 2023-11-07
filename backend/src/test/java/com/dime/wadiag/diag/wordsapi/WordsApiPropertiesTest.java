package com.dime.wadiag.diag.wordsapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = WordsApiProperties.class)
@TestPropertySource("classpath:private/conf.properties")
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
class WordsApiPropertiesTest {

    @Autowired
    private WordsApiProperties wordsApiProperties;

    @Test
    @DisplayName("Test wordsApi fill properties")
    void test_load_properties_file_then_all_fields_are_set() {
        assertThat(wordsApiProperties.getKey()).isNotNull();
        assertThat(wordsApiProperties.getKey()).hasSize(50);
    }
}
