package com.dime.wadiag.diag.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.dime.wadiag.diag.model.Word;
import com.github.javafaker.Faker;

@DataJpaTest
class WordRepositoryTest {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final Faker faker = new Faker();

    @DisplayName("Should save and retrieve word")
    @Test
    void test_save_and_retrieve_word() {
        Word word = new Word(faker.lorem().word());
        entityManager.persistAndFlush(word);

        Word retrievedWord = wordRepository.findById(word.getId()).orElse(null);
        assertThat(retrievedWord).isNotNull();
        assertThat(retrievedWord.getName()).isEqualTo(word.getName());
    }

    @DisplayName("Should find word by name")
    @Test
    void test_find_by_name() {
        String randomName = faker.lorem().word();
        Word word = new Word(randomName);
        entityManager.persistAndFlush(word);

        Word retrievedWord = wordRepository.findByName(randomName);
        assertThat(retrievedWord).isNotNull();
        assertThat(retrievedWord.getName()).isEqualTo(randomName);
    }

    @DisplayName("Should find all word already save")
    @Test
    void test_find_all() {

        List<String> list = new ArrayList<>();
        list.add(faker.lorem().word());
        list.add(faker.lorem().word());

        entityManager.persistAndFlush(new Word(list.get(0)));
        entityManager.persistAndFlush(new Word(list.get(1)));

        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).extracting(Word::getName)
                .containsExactlyInAnyOrderElementsOf(list);
    }

}
