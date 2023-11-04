package com.dime.wadiag.diag.word;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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
        Word word = Word.builder().name(faker.lorem().word()).build();
        entityManager.persistAndFlush(word);

        Word retrievedWord = wordRepository.findById(word.getId()).orElse(null);
        assertThat(retrievedWord).isNotNull();
        assertThat(retrievedWord.getName()).isEqualTo(word.getName());
    }

    @DisplayName("Should find word by name")
    @Test
    void test_find_by_name() {
        String randomName = faker.lorem().word();
        Word word = Word.builder().name(randomName).build();
        entityManager.persistAndFlush(word);

        Word retrievedWord = wordRepository.findByName(randomName);
        assertThat(retrievedWord).isNotNull();
        assertThat(retrievedWord.getName()).isEqualTo(randomName);
    }

    @DisplayName("Should find all word already save")
    @Test
    void test_find_all() {
        Word word = Word.builder().name(faker.lorem().word()).build();
        entityManager.persistAndFlush(word);

        Word word1 = Word.builder().name(faker.lorem().word()).build();
        entityManager.persistAndFlush(word1);

        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList)
                .isNotNull()
                .hasSize(2);
    }
}
