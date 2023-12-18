package com.dime.wadiag.diag.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(term)));

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
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(existingTerm)));

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
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(existingTerm)));

        verify(service, times(1)).findByWord(word);
        verify(service, never()).save(word);
    }

    @DisplayName("Should handle missing word parameter and return 404 Not found")
    @Test
    void test_missing_word_parameter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/terms/"))
                .andExpect(status().is(404));
    }

    @DisplayName("Should retrieve all terms")
    @Test
    void test_find_all_words_endpoint() throws Exception {
        List<Term> termList = Arrays.asList(
                new Term(faker.lorem().word()),
                new Term(faker.lorem().word()),
                new Term(faker.lorem().word()));

        when(service.findAll()).thenReturn(termList);

        mockMvc.perform(get("/rest/terms").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(termList)));
    }

    @DisplayName("Should returns a 204 No Content response when there are no terms in the database")
    @Test
    void test_returns_no_content_when_no_terms_exist() throws Exception {

        when(service.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/rest/terms").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @DisplayName("Should returns a 200 and find by Id valid")
    @Test
    void test_valid_id() throws Exception {
        // Arrange
        Long termId = 1L;
        Term mockTerm = new Term();
        when(service.findById(anyLong())).thenReturn(Optional.of(mockTerm));

        // Act and Assert
        mockMvc.perform(get("/rest/terms/{id}", termId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(service, times(1)).findById(termId);
    }

    @DisplayName("Should returns a 204 when id not exits")
    @Test
    void test_invalid_id() throws Exception {

        // Act and Assert
        mockMvc.perform(get("/rest/terms/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(service, times(1)).findById(2L);
    }

    @DisplayName("Verify that the method returns a 200 status code when term is deleted")
    @Test
    void test_returns_200_when_word_deleted() throws Exception {
        String wordToDelete = faker.lorem().word();
        int deletedCount = 1;
        when(service.deleteByWord(wordToDelete)).thenReturn(deletedCount);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/terms/{word}", wordToDelete.toUpperCase())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1 term deleted successfully"));
        verify(service, times(1)).deleteByWord(wordToDelete);
    }

    @DisplayName("Verify that the method returns a 200 status code when terms is deleted")
    @Test
    void test_returns_200_when_words_deleted() throws Exception {
        String wordToDelete = faker.lorem().word();
        int deletedCount = 3;
        when(service.deleteByWord(wordToDelete)).thenReturn(deletedCount);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/terms/{word}", wordToDelete.toUpperCase())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(("3 terms deleted successfully")));
        verify(service, times(1)).deleteByWord(wordToDelete);
    }

    @DisplayName("Verify that the method returns a 204 status code when the word to be deleted is not found")
    @Test
    void test_returns_204_status_code_when_word_not_found() throws Exception {
        String wordToDelete = faker.lorem().word();
        int deletedCount = 0;
        when(service.deleteByWord(wordToDelete)).thenReturn(deletedCount);

        // Act and Assert
        mockMvc.perform(delete("/rest/terms/{word}", wordToDelete.toUpperCase())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
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
    void test_delete_word_exception() throws Exception {
        String wordToDelete = faker.lorem().word();
        when(service.deleteByWord(wordToDelete)).thenThrow(new RuntimeException("Simulated exception"));

        mockMvc.perform(delete("/rest/terms/{word}", wordToDelete)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error when deleting term"));

        verify(service, times(1)).deleteByWord(wordToDelete);
    }
}