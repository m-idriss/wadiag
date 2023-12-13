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

import com.dime.wadiag.diag.model.Term;
import com.dime.wadiag.diag.repository.TermRepository;
import com.github.javafaker.Faker;

@ExtendWith(MockitoExtension.class)
class TermServiceImplTest {

    private final Faker faker = new Faker();
    @Mock
    private TermRepository termRepository;

    @Mock
    private WordsApiServiceImpl wordsApiService;

    @InjectMocks
    private TermServiceImpl termServiceImpl;

    @DisplayName("Saving a new word should create a Word entity with the given name and return it")
    @Test
    void test_save_new_word() throws IOException {
        // Given
        String word = faker.lorem().word();
        Term mockApiResponse = new Term(1L, word, Set.of(faker.lorem().word(), faker.lorem().word()));

        // Mock external API response
        Mockito.when(wordsApiService.getSynonymsForWord(word)).thenReturn(mockApiResponse);

        // Mock repository save
        Mockito.when(termRepository.save(any(Term.class))).thenAnswer(invocation -> {
            Term savedTerm = invocation.getArgument(0);
            savedTerm.setId(1L); // Assign a mock ID
            return savedTerm;
        });

        // When
        Term savedTerm = termServiceImpl.save(word);

        // Then
        assertThat(savedTerm).isNotNull();
        assertThat(savedTerm.getId()).isNotNull(); // ID should be assigned after saving
        assertThat(savedTerm.getWord()).isEqualTo(word);
        assertThat(savedTerm.getSynonyms()).isEqualTo(mockApiResponse.getSynonyms());

        // Verify that external service was called
        Mockito.verify(wordsApiService, Mockito.times(1)).getSynonymsForWord(word);

        // Verify that repository save was called
        Mockito.verify(termRepository, Mockito.times(1)).save(any(Term.class));

    }

    @DisplayName("Finding a word by name should return the corresponding Word entity")
    @Test()
    void test_find_by_word() {
        // Arrange
        TermRepository dao = Mockito.mock(TermRepository.class);
        WordsApiServiceImpl wordsApiService = Mockito.mock(WordsApiServiceImpl.class);
        TermServiceImpl termService = new TermServiceImpl(dao, wordsApiService);
        String word = faker.lorem().word();
        Term expectedEntity = new Term(word);
        Mockito.when(dao.findByWord(word)).thenReturn(expectedEntity);

        // Act
        Term result = termService.findByWord(word);

        // Assert
        assertEquals(expectedEntity, result);
        Mockito.verify(dao, Mockito.times(1)).findByWord(word);
    }

    @DisplayName("Finding all words should return a list of all Word entities in the repository")
    @Test()
    void test_find_all() {
        // Arrange
        TermRepository dao = Mockito.mock(TermRepository.class);
        WordsApiServiceImpl wordsApiService = Mockito.mock(WordsApiServiceImpl.class);
        TermServiceImpl termService = new TermServiceImpl(dao, wordsApiService);
        List<Term> expectedList = new ArrayList<>();
        expectedList.add(new Term(faker.lorem().word()));
        expectedList.add(new Term(faker.lorem().word()));
        Mockito.when(dao.findAll()).thenReturn(expectedList);

        // Act
        List<Term> result = termService.findAll();

        // Assert
        assertEquals(expectedList, result);
        Mockito.verify(dao, Mockito.times(1)).findAll();
    }

    @DisplayName("Should return the Word entity with the given name if it exists in the repository")
    @Test
    void test_return_term_by_word_if_entity_exists() {
        // Arrange
        String word = faker.lorem().word();
        Term expectedEntity = new Term(word);
        Mockito.when(termRepository.findByWord(word)).thenReturn(expectedEntity);

        // Act
        Term result = termServiceImpl.findByWord(word);

        // Assert
        assertEquals(expectedEntity, result);
        Mockito.verify(termRepository, Mockito.times(1)).findByWord(word);
    }

    @DisplayName("Deletes a word that exists in the database and returns the count of deleted words")
    @Test
    void test_delete_existing_term() {
        // Arrange
        String word = faker.lorem().word();
        int expectedDeletedCount = 3;
        Mockito.when(termRepository.deleteByWord(word)).thenReturn(expectedDeletedCount);

        // Act
        int actualDeletedCount = termServiceImpl.deleteByWord(word);

        // Assert
        assertEquals(expectedDeletedCount, actualDeletedCount, "Deleted count should match");
        Mockito.verify(termRepository, Mockito.times(1)).deleteByWord(word);
    }

}
