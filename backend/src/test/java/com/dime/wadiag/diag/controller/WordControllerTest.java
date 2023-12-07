package com.dime.wadiag.diag.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.dime.wadiag.diag.model.Word;
import com.dime.wadiag.diag.service.WordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

@SpringBootTest
@AutoConfigureMockMvc
class WordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final Faker faker = new Faker();

    @MockBean
    private WordService service;

    @DisplayName("Should save a new word and return 201 Created")
    @Test
    void test_save_new_word() throws Exception {
        String name = faker.lorem().word();
        Word word = new Word(name);

        when(service.findByName(name)).thenReturn(null);
        when(service.save(name)).thenReturn(word);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/words/" + name))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(word)));

        verify(service, times(1)).findByName(name);
        verify(service, times(1)).save(name);
    }

    @DisplayName("Should return existing word and return 200 OK")
    @Test
    void test_return_existing_word() throws Exception {
        String name = faker.lorem().word();
        Word existingWord = new Word(name);

        when(service.findByName(name)).thenReturn(existingWord);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/words/" + name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(existingWord)));

        verify(service, times(1)).findByName(name);
        verify(service, never()).save(name);
    }

    @DisplayName("Should handle existing word and return 200 OK")
    @Test
    void test_handle_existing_word() throws Exception {
        String name = faker.lorem().word();
        Word existingWord = new Word(name);

        when(service.findByName(name)).thenReturn(existingWord);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/words/" + name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(existingWord)));

        verify(service, times(1)).findByName(name);
        verify(service, never()).save(name);
    }

    @DisplayName("Should handle missing word parameter and return 404 Not found")
    @Test
    void test_missing_word_parameter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/words/"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @DisplayName("Should retrieve all words")
    @Test
    void test_find_all_words_endpoint() throws Exception {
        // Assuming you have a list of words in your WordService or a database
        List<Word> wordList = Arrays.asList(
                new Word(faker.lorem().word()),
                new Word(faker.lorem().word()),
                new Word(faker.lorem().word()));

        when(service.findAll()).thenReturn(wordList);

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/words").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(wordList)));
    }

    @DisplayName("Verify that the method returns a 200 status code when word is deleted")
    @Test
    void test_returns_200_when_word_not_found() throws Exception {
        String wordToDelete = faker.lorem().word();
        int deletedCount = 3;

        // Mocking the behavior of the service
        when(service.deleteByName(wordToDelete)).thenReturn(deletedCount);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/words/{word}", wordToDelete)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(deletedCount + " word(s) deleted successfully"));

        // Verify that the service method was called with the correct argument
        verify(service, times(1)).deleteByName(wordToDelete);
    }

    @DisplayName("Verify that the method returns a 204 status code when the word to be deleted is not found")
    @Test
    void test_returns_204_status_code_when_word_not_found() throws Exception {
        String wordToDelete = faker.lorem().word();
        int deletedCount = 0;

        // Mocking the behavior of the service
        when(service.deleteByName(wordToDelete)).thenReturn(deletedCount);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/words/{word}", wordToDelete)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()));

        // Verify that the service method was called with the correct argument
        verify(service, times(1)).deleteByName(wordToDelete);
    }

    @DisplayName("Verify that the method returns a 500 status code and an error message when an exception is thrown during the deletion process")
    @Test
    void test_returns_500_status_code_and_error_message_when_exception_thrown_during_deletion() {
        // Arrange
        WordController wordController = new WordController();

        // Act
        ResponseEntity<String> response = wordController.deleteByName("exception");

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}