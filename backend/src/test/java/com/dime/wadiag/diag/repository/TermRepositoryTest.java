package com.dime.wadiag.diag.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.dime.wadiag.diag.model.Term;
import com.github.javafaker.Faker;

@DataJpaTest
class TermRepositoryTest {

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final Faker faker = new Faker();

    @DisplayName("Should save and retrieve word")
    @Test
    void test_save_and_retrieve_word() {
        Term word = new Term(faker.lorem().word());
        entityManager.persistAndFlush(word);

        Term retrievedWord = termRepository.findById(word.getId()).orElse(null);
        assertThat(retrievedWord).isNotNull();
        assertThat(retrievedWord.getWord()).isEqualTo(word.getWord());
    }

    @DisplayName("Should find word by name")
    @Test
    void test_find_by_word() {
        String word = faker.lorem().word();
        Term term = new Term(word);
        entityManager.persistAndFlush(term);

        Term retrievedWord = termRepository.findByWord(word);
        assertThat(retrievedWord).isNotNull();
        assertThat(retrievedWord.getWord()).isEqualTo(word);
    }

    @DisplayName("Should find all word already save")
    @Test
    void test_find_all() {

        List<String> list = new ArrayList<>();
        list.add("toto");
        list.add("tata");

        entityManager.persistAndFlush(new Term(list.get(0)));
        entityManager.persistAndFlush(new Term(list.get(1)));

        List<Term> wordList = termRepository.findAll();
        assertThat(wordList).extracting(Term::getWord)
                .containsExactlyInAnyOrderElementsOf(list);
    }

}
