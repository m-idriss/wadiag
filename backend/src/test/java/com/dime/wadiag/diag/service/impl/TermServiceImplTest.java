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
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dime.wadiag.diag.model.Term;
import com.dime.wadiag.diag.repository.TermRepository;
import com.github.javafaker.Faker;

@ExtendWith(MockitoExtension.class)
class TermServiceImplTest {

    private final Faker faker = new Faker();
    @Mock
    private TermRepository repository;

    @Mock
    private WordsApiServiceImpl wordsApiService;

    @InjectMocks
    private TermServiceImpl service;

    @DisplayName("Saving a new word should create a Word entity with the given name and return it")
    @Test
    void test_create_new_word() throws IOException {
        // Arrange
        String word = faker.lorem().word();
        Term mockApiResponse = new Term(1L, word, Set.of(faker.lorem().word(), faker.lorem().word()));
        when(wordsApiService.getSynonymsForWord(word)).thenReturn(mockApiResponse);
        when(repository.save(any(Term.class))).thenAnswer(invocation -> {
            Term savedTerm = invocation.getArgument(0);
            savedTerm.setId(1L);
            return savedTerm;
        });

        // Act
        Term createdTerm = service.create(word).get();

        // Assert
        assertThat(createdTerm).isNotNull();
        assertThat(createdTerm.getId()).isNotNull();
        assertThat(createdTerm.getWord()).isEqualTo(word);
        assertThat(createdTerm.getSynonyms()).isEqualTo(mockApiResponse.getSynonyms());
        verify(wordsApiService, times(1)).getSynonymsForWord(word);
        verify(repository, times(1)).save(any(Term.class));

    }

    @DisplayName("Finding a word by name should return the corresponding Word entity")
    @Test()
    void test_find_by_word() {
        // Arrange
        TermRepository dao = mock(TermRepository.class);
        WordsApiServiceImpl wordsApiService = mock(WordsApiServiceImpl.class);
        TermServiceImpl termService = new TermServiceImpl(dao, wordsApiService);
        String word = faker.lorem().word();
        Term expectedEntity = new Term(word);
        when(dao.findByWord(word)).thenReturn(expectedEntity);

        // Act
        Term result = termService.findByWord(word).get();

        // Assert
        assertEquals(expectedEntity, result);
        verify(dao, times(1)).findByWord(word);
    }

    @DisplayName("Finding all words should return a list of all Word entities in the repository")
    @Test()
    void test_find_all() {
        // Arrange
        TermRepository dao = mock(TermRepository.class);
        WordsApiServiceImpl wordsApiService = mock(WordsApiServiceImpl.class);
        TermServiceImpl termService = new TermServiceImpl(dao, wordsApiService);
        List<Term> expectedList = new ArrayList<>();
        expectedList.add(new Term(faker.lorem().word()));
        expectedList.add(new Term(faker.lorem().word()));
        when(dao.findAll()).thenReturn(expectedList);

        // Act
        List<Term> result = termService.findAll().get();

        // Assert
        assertEquals(expectedList, result);
        verify(dao, times(1)).findAll();
    }

    @DisplayName("Should return the Word entity with the given name if it exists in the repository")
    @Test
    void test_return_term_by_word_if_entity_exists() {
        // Arrange
        String word = faker.lorem().word();
        Term expectedEntity = new Term(word);
        when(repository.findByWord(word)).thenReturn(expectedEntity);

        // Act
        Term result = service.findByWord(word).get();

        // Assert
        assertEquals(expectedEntity, result);
        verify(repository, times(1)).findByWord(word);
    }

    @DisplayName("Deletes a word that exists in the database and returns the count of deleted words")
    @Test
    void test_delete_existing_term() {
        // Arrange
        String word = faker.lorem().word();
        int expectedDeletedCount = 3;
        when(repository.deleteByWord(word)).thenReturn(expectedDeletedCount);

        // Act
        int actualDeletedCount = service.deleteByWord(word).get();

        // Assert
        assertEquals(expectedDeletedCount, actualDeletedCount, "Deleted count should match");
        verify(repository, times(1)).deleteByWord(word);
    }

    @DisplayName("Returns an Optional object containing the Term with the given id, if it exists in the repository")
    @Test
    void test_returns_optional_with_existing_id() {
        // Arrange
        Long id = 1L;
        Term term = new Term();
        term.setId(id);
        Optional<Term> expected = Optional.of(term);
        when(repository.findById(id)).thenReturn(expected);

        // Act
        Optional<Term> result = service.findById(id);

        // Assert
        assertEquals(expected, result);
        verify(repository, times(1)).findById(id);

    }

    @DisplayName("Returns an empty Optional object if no Term with the given id exists in the repository")
    @Test
    void test_returns_empty_optional_with_non_existing_id() {
        // Arrange
        Long id = 1L;
        Optional<Term> expected = Optional.empty();
        when(repository.findById(id)).thenReturn(expected);

        // Act
        Optional<Term> result = service.findById(id);

        // Assert
        assertEquals(expected, result);
        verify(repository, times(1)).findById(id);
    }

}
