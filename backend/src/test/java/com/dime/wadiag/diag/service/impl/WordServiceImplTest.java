package com.dime.wadiag.diag.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.dime.wadiag.diag.wordsapi.ResourceNotFoundException;
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
    void test_save_new_word() throws ResourceNotFoundException, IOException {
        // Given
        String wordName = "exampleWord";
        WordDto mockApiResponse = new WordDto();
        mockApiResponse.setWord(wordName);
        mockApiResponse.setSynonyms(Set.of("synonym1", "synonym2"));

        // Mock external API response
        when(wordsApiService.getSynonymsForWord(wordName)).thenReturn(mockApiResponse);

        // Mock repository save
        when(wordRepository.save(any(Word.class))).thenAnswer(invocation -> {
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
        WordRepository dao = mock(WordRepository.class);
        WordsApiServiceImpl wordsApiService = mock(WordsApiServiceImpl.class);
        WordServiceImpl wordService = new WordServiceImpl(dao, wordsApiService);

        String word = "test";
        Word expectedEntity = new Word(word);
        when(dao.findByName(word)).thenReturn(expectedEntity);

        Word result = wordService.findByName(word);

        assertEquals(expectedEntity, result);
        verify(dao, times(1)).findByName(word);
    }

    @DisplayName("Finding all words should return a list of all Word entities in the repository")
    @Test()
    void test_find_all() {
        WordRepository dao = mock(WordRepository.class);
        WordsApiServiceImpl wordsApiService = mock(WordsApiServiceImpl.class);
        WordServiceImpl wordService = new WordServiceImpl(dao, wordsApiService);

        List<Word> expectedList = new ArrayList<>();
        expectedList.add(new Word(faker.lorem().word()));
        expectedList.add(new Word(faker.lorem().word()));
        when(dao.findAll()).thenReturn(expectedList);

        List<Word> result = wordService.findAll();

        assertEquals(expectedList, result);
        verify(dao, times(1)).findAll();
    }

}
