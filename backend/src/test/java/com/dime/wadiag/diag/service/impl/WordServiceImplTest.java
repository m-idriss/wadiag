package com.dime.wadiag.diag.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dime.wadiag.diag.dto.WordDto;
import com.dime.wadiag.diag.model.Word;
import com.dime.wadiag.diag.repository.WordRepository;
import com.github.javafaker.Faker;

@ExtendWith(MockitoExtension.class)
class WordServiceImplTest {

    private final Faker faker = new Faker();
    @Mock
    private WordRepository wordRepository;

    @Mock
    private WordsApiServiceImpl wordsApiService;

    @InjectMocks
    private WordServiceImpl wordServiceImpl;

    @DisplayName("Saving a new word should create a Word entity with the given name and return it")
    @Test
    void test_save_new_word() throws IOException {
        // Given
        String wordName = faker.lorem().word();
        WordDto mockApiResponse = new WordDto();
        mockApiResponse.setWord(wordName);
        mockApiResponse.setSynonyms(Set.of(faker.lorem().word(), faker.lorem().word()));

        // Mock external API response
        Mockito.when(wordsApiService.getSynonymsForWord(wordName)).thenReturn(mockApiResponse);

        // Mock repository save
        Mockito.when(wordRepository.save(any(Word.class))).thenAnswer(invocation -> {
            Word savedWord = invocation.getArgument(0);
            savedWord.setId(1L); // Assign a mock ID
            return savedWord;
        });

        // When
        Word savedWord = wordServiceImpl.save(wordName);

        // Then
        assertThat(savedWord).isNotNull();
        assertThat(savedWord.getId()).isNotNull(); // ID should be assigned after saving
        assertThat(savedWord.getName()).isEqualTo(wordName);
        assertThat(savedWord.getSynonyms()).isEqualTo(mockApiResponse.getSynonyms());

        // Verify that external service was called
        Mockito.verify(wordsApiService, Mockito.times(1)).getSynonymsForWord(wordName);

        // Verify that repository save was called
        Mockito.verify(wordRepository, Mockito.times(1)).save(any(Word.class));

    }

    @DisplayName("Finding a word by name should return the corresponding Word entity")
    @Test()
    void test_find_by_name() {
        // Arrange
        WordRepository dao = Mockito.mock(WordRepository.class);
        WordsApiServiceImpl wordsApiService = Mockito.mock(WordsApiServiceImpl.class);
        WordServiceImpl wordService = new WordServiceImpl(dao, wordsApiService);
        String name = faker.lorem().word();
        Word expectedEntity = new Word(name);
        Mockito.when(dao.findByName(name)).thenReturn(expectedEntity);

        // Act
        Word result = wordService.findByName(name);

        // Assert
        assertEquals(expectedEntity, result);
        Mockito.verify(dao, Mockito.times(1)).findByName(name);
    }

    @DisplayName("Finding all words should return a list of all Word entities in the repository")
    @Test()
    void test_find_all() {
        // Arrange
        WordRepository dao = Mockito.mock(WordRepository.class);
        WordsApiServiceImpl wordsApiService = Mockito.mock(WordsApiServiceImpl.class);
        WordServiceImpl wordService = new WordServiceImpl(dao, wordsApiService);
        List<Word> expectedList = new ArrayList<>();
        expectedList.add(new Word(faker.lorem().word()));
        expectedList.add(new Word(faker.lorem().word()));
        Mockito.when(dao.findAll()).thenReturn(expectedList);

        // Act
        List<Word> result = wordService.findAll();

        // Assert
        assertEquals(expectedList, result);
        Mockito.verify(dao, Mockito.times(1)).findAll();
    }

    @DisplayName("Should return the Word entity with the given name if it exists in the repository")
    @Test
    void test_return_word_by_name_if_entity_exists() {
        // Arrange
        String name = faker.lorem().word();
        Word expectedEntity = new Word(name);
        Mockito.when(wordRepository.findByName(name)).thenReturn(expectedEntity);

        // Act
        Word result = wordServiceImpl.findByName(name);

        // Assert
        assertEquals(expectedEntity, result);
        Mockito.verify(wordRepository, Mockito.times(1)).findByName(name);
    }

    @DisplayName("Deletes a word that exists in the database and returns the count of deleted words")
    @Test
    void test_delete_existing_word() {
        // Arrange
        String name = faker.lorem().word();
        int expectedDeletedCount = 3;
        Mockito.when(wordRepository.deleteByName(name)).thenReturn(expectedDeletedCount);

        // Act
        int actualDeletedCount = wordServiceImpl.deleteByName(name);

        // Assert
        assertEquals(expectedDeletedCount, actualDeletedCount, "Deleted count should match");
        Mockito.verify(wordRepository, Mockito.times(1)).deleteByName(name);
    }

}
