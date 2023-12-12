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

import com.dime.wadiag.diag.model.Term;
import com.dime.wadiag.diag.service.TermService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

@SpringBootTest
@AutoConfigureMockMvc
class TermControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final Faker faker = new Faker();

    @MockBean
    private TermService service;

    @DisplayName("Should save a new word and return 201 Created")
    @Test
    void test_save_new_word() throws Exception {
        String word = faker.lorem().word();
        Term term = new Term(word);

        when(service.findByWord(word)).thenReturn(null);
        when(service.save(word)).thenReturn(term);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/terms/{word}", word.toUpperCase()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(term)));

        verify(service, times(1)).findByWord(word);
        verify(service, times(1)).save(word);
    }

    @DisplayName("Should return existing word and return 200 OK")
    @Test
    void test_return_existing_word() throws Exception {
        String word = faker.lorem().word();
        Term existingTerm = new Term(word);

        when(service.findByWord(word)).thenReturn(existingTerm);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/terms/{word}", word.toUpperCase()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(existingTerm)));

        verify(service, times(1)).findByWord(word);
        verify(service, never()).save(word);
    }

    @DisplayName("Should handle existing word and return 200 OK")
    @Test
    void test_handle_existing_word() throws Exception {
        String word = faker.lorem().word();
        Term existingTerm = new Term(word);

        when(service.findByWord(word)).thenReturn(existingTerm);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/terms/{word}", word.toUpperCase()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(existingTerm)));

        verify(service, times(1)).findByWord(word);
        verify(service, never()).save(word);
    }

    @DisplayName("Should handle missing word parameter and return 404 Not found")
    @Test
    void test_missing_word_parameter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/terms/"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @DisplayName("Should retrieve all terms")
    @Test
    void test_find_all_words_endpoint() throws Exception {
        // Assuming you have a list of words in your TermService or a database
        List<Term> termList = Arrays.asList(
                new Term(faker.lorem().word()),
                new Term(faker.lorem().word()),
                new Term(faker.lorem().word()));

        when(service.findAll()).thenReturn(termList);

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/terms").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(termList)));
    }

    @DisplayName("Verify that the method returns a 200 status code when term is deleted")
    @Test
    void test_returns_200_when_word_deleted() throws Exception {
        String wordToDelete = faker.lorem().word();
        int deletedCount = 1;

        // Mocking the behavior of the service
        when(service.deleteByWord(wordToDelete)).thenReturn(deletedCount);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/terms/{word}", wordToDelete.toUpperCase())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1 term deleted successfully"));

        // Verify that the service method was called with the correct argument
        verify(service, times(1)).deleteByWord(wordToDelete);
    }

    @DisplayName("Verify that the method returns a 200 status code when terms is deleted")
    @Test
    void test_returns_200_when_words_deleted() throws Exception {
        String wordToDelete = faker.lorem().word();
        int deletedCount = 3;

        // Mocking the behavior of the service
        when(service.deleteByWord(wordToDelete)).thenReturn(deletedCount);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/terms/{word}", wordToDelete.toUpperCase())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(("3 terms deleted successfully")));

        // Verify that the service method was called with the correct argument
        verify(service, times(1)).deleteByWord(wordToDelete);
    }

    @DisplayName("Verify that the method returns a 204 status code when the word to be deleted is not found")
    @Test
    void test_returns_204_status_code_when_word_not_found() throws Exception {
        String wordToDelete = faker.lorem().word();
        int deletedCount = 0;

        // Mocking the behavior of the service
        when(service.deleteByWord(wordToDelete)).thenReturn(deletedCount);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/terms/{word}", wordToDelete.toUpperCase())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()));

        // Verify that the service method was called with the correct argument
        verify(service, times(1)).deleteByWord(wordToDelete);
    }

    @DisplayName("Verify that the method returns a 500 status code and an error message when an exception is thrown during the deletion process")
    @Test
    void test_returns_500_status_code_and_error_message_when_exception_thrown_during_deletion() {
        // Arrange
        TermController wordController = new TermController();

        // Act
        ResponseEntity<String> response = wordController.deleteByWord("exception");

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @DisplayName("Should handle exception and return 500 Internal Server Error")
    @Test
    void testDeleteWordException() throws Exception {
        String wordToDelete = faker.lorem().word();

        when(service.deleteByWord(wordToDelete)).thenThrow(new RuntimeException("Simulated exception"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/terms/{word}", wordToDelete))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Error when deleting term"));

        verify(service, times(1)).deleteByWord(wordToDelete);
    }
}