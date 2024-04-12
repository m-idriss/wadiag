package com.dime.wadiag;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dime.wadiag.diag.model.Term;

class TermTest {

    @Test
    @DisplayName("Should create Term object with correct properties")
    void test_create_term() {
        // Arrange
        long id = 1;
        String word = "test";
        Set<String> synonyms = new HashSet<>();
        synonyms.add("synonym1");
        synonyms.add("synonym2");

        // Act
        Term term = new Term(id, word, synonyms);

        // Assert
        assertThat(term.getId()).isEqualTo(id);
        assertThat(term.getWord()).isEqualTo(word);
        assertThat(term.getSynonyms()).isEqualTo(synonyms);
    }

    @Test
    @DisplayName("Should create Term object with empty synonyms set")
    void test_create_term_with_empty_synonyms() {
        // Arrange
        long id = 1;
        String word = "test";
        Set<String> synonyms = new HashSet<>();

        // Act
        Term term = new Term(id, word, synonyms);

        // Assert
        assertThat(term.getId()).isEqualTo(id);
        assertThat(term.getWord()).isEqualTo(word);
        assertThat(term.getSynonyms()).isEqualTo(synonyms);
    }

    @Test
    @DisplayName("Should create Term object with null synonyms set")
    void test_create_term_with_null_synonyms() {
        // Arrange
        long id = 1;
        String word = "test";
        Set<String> synonyms = null;

        // Act
        Term term = new Term(id, word, synonyms);

        // Assert
        assertThat(term.getId()).isEqualTo(id);
        assertThat(term.getWord()).isEqualTo(word);
        assertThat(term.getSynonyms()).isNull();
    }

    @Test
    @DisplayName("Should test equality")
    void test_equals_Symmetric() {
        Term x = new Term("toto"); // equals and hashCode check name field value
        Term y = new Term("toto");
        assertThat(x.equals(y)).isEqualTo(y.equals(x));
        assertThat(x).hasSameHashCodeAs(y);
    }

}