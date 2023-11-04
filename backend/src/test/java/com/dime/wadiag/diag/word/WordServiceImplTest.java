package com.dime.wadiag.diag.word;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WordServiceImplTest {

    @DisplayName("Saving a new word should create a Word entity with the given name and return it")
    @Test()
    void test_save_new_word() {
        WordRepository dao = mock(WordRepository.class);
        WordServiceImpl wordService = new WordServiceImpl(dao);

        String word = "test";
        Word expectedEntity = Word.builder()
                .name(word)
                .build();
        when(dao.save(any(Word.class))).thenReturn(expectedEntity);

        Word result = wordService.save(word);

        assertEquals(expectedEntity, result);
        verify(dao, times(1)).save(any(Word.class));
    }

    @DisplayName("Finding a word by name should return the corresponding Word entity")
    @Test()
    void test_find_by_name() {
        WordRepository dao = mock(WordRepository.class);
        WordServiceImpl wordService = new WordServiceImpl(dao);

        String word = "test";
        Word expectedEntity = Word.builder()
                .name(word)
                .build();
        when(dao.findByName(word)).thenReturn(expectedEntity);

        Word result = wordService.findByName(word);

        assertEquals(expectedEntity, result);
        verify(dao, times(1)).findByName(word);
    }

    @DisplayName("Finding all words should return a list of all Word entities in the repository")
    @Test()
    void test_find_all() {
        WordRepository dao = mock(WordRepository.class);
        WordServiceImpl wordService = new WordServiceImpl(dao);

        List<Word> expectedList = new ArrayList<>();
        expectedList.add(Word.builder().name("word1").build());
        expectedList.add(Word.builder().name("word2").build());
        when(dao.findAll()).thenReturn(expectedList);

        List<Word> result = wordService.findAll();

        assertEquals(expectedList, result);
        verify(dao, times(1)).findAll();
    }

}
