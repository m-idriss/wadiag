package com.dime.wadiag.diag.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dime.wadiag.diag.model.Term;
import com.dime.wadiag.diag.service.TermService;
import com.github.javafaker.Faker;

@SpringBootTest
@AutoConfigureMockMvc
class TermControllerTest {

        @Autowired
        private MockMvc mockMvc;

        private final Faker faker = new Faker();

        @MockBean
        private TermService service;

        @DisplayName("Should save a new word and return 201 Created")
        @Test
        void test_save_new_word() throws Exception {
                String word = faker.lorem().word();
                Term term = new Term(word);
                term.setId(123L);
                term.setSynonyms(new HashSet<>(Arrays.asList("tata", "toto")));

                when(service.findByWord(word)).thenReturn(Optional.empty());
                when(service.create(word)).thenReturn(Optional.of(term));

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/terms/{word}", word.toUpperCase())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(123))
                                .andExpect(jsonPath("$.word").value(word))
                                .andExpect(jsonPath("$.synonyms", hasItem("tata")))
                                .andExpect(jsonPath("$.synonyms", hasItem("toto")))
                                .andExpect(jsonPath("$.synonyms", hasSize(2)))
                                .andExpect(jsonPath("$._links.self.href", matchesPattern("http.*/api/v1/terms/123")))
                                .andExpect(jsonPath("$._links.collection.href", matchesPattern("http.*/api/v1/terms")));

                verify(service, times(1)).findByWord(word);
                verify(service, times(1)).create(word);
        }

        @DisplayName("Should save an old word and return 200 OK")
        @Test
        void test_save_old_word() throws Exception {
                String word = faker.lorem().word();
                Term term = new Term(word);
                term.setId(345L);

                when(service.findByWord(word)).thenReturn(Optional.of(term));

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/terms/{word}", word.toUpperCase())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$._links.self.href", matchesPattern("http.*/api/v1/terms/345")))
                                .andExpect(jsonPath("$._links.collection.href", matchesPattern("http.*/api/v1/terms")));
                ;
                verify(service, times(1)).findByWord(word);
                verify(service, times(0)).create(word);
        }

        @DisplayName("Should handle missing word parameter and return 404 Not found")
        @Test
        void test_missing_word_parameter() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/terms/"))
                                .andExpect(status().isNotFound());
        }

        @DisplayName("Should retrieve all terms")
        @Test
        void test_find_all_words_endpoint() throws Exception {
                List<Term> termList = Arrays.asList(
                                new Term(faker.lorem().word()),
                                new Term(faker.lorem().word()),
                                new Term(789L, "test", new HashSet<>(Arrays.asList("zaza", "zoto"))));

                when(service.findAll()).thenReturn(Optional.of(termList));

                mockMvc.perform(get("/api/v1/terms")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$._embedded.termList", hasSize(termList.size())))
                                .andExpect(
                                                jsonPath("$._embedded.termList[2]._links.self.href",
                                                                matchesPattern("http.*/api/v1/terms/789")))
                                .andExpect(jsonPath("$._embedded.termList[2].synonyms", hasItem("zaza")))
                                .andExpect(jsonPath("$._links.self.href", matchesPattern("http.*/api/v1/terms")));
                ;
        }

        @DisplayName("Should return a 200 No Content response when there are no terms in the database")
        @Test
        void test_returns_no_content_when_no_terms_exist() throws Exception {

                when(service.findAll()).thenReturn(Optional.of(new ArrayList<>()));

                mockMvc.perform(get("/api/v1/terms")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$._links.self.href", matchesPattern("http.*/api/v1/terms")));

        }

        @DisplayName("Should return a 200 and find by Id valid")
        @Test
        void test_valid_id() throws Exception {
                // Arrange
                Long termId = 1L;
                Term term = new Term();

                when(service.findById(anyLong())).thenReturn(Optional.of(term));

                // Act and Assert
                mockMvc.perform(get("/api/v1/terms/{id}", termId)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());

                verify(service, times(1)).findById(termId);
        }

        @DisplayName("Should return a 404 when id not exists")
        @Test
        void test_invalid_id() throws Exception {
                int id = 2;

                // Act and Assert
                mockMvc.perform(get("/api/v1/terms/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.message")
                                                .value(String.format("Term with id [%s] not found", id)));

                verify(service, times(1)).findById(2L);
        }

        @DisplayName("Verify that the method returns a 204 status code when term is deleted")
        @Test
        void test_returns_200_when_word_deleted() throws Exception {
                String wordToDelete = faker.lorem().word();
                int deletedCount = 1;
                when(service.deleteByWord(wordToDelete)).thenReturn(Optional.of(deletedCount));

                // Act and Assert
                mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/terms/{word}", wordToDelete.toUpperCase())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());
                verify(service, times(1)).deleteByWord(wordToDelete);
        }

        @DisplayName("Verify that the method returns a 204 status code when the word to be deleted is not found")
        @Test
        void test_returns_204_status_code_when_word_not_found() throws Exception {
                String wordToDelete = faker.lorem().word();
                int deletedCount = 0;
                when(service.deleteByWord(wordToDelete)).thenReturn(Optional.of(deletedCount));

                // Act and Assert
                mockMvc.perform(delete("/api/v1/terms/{word}", wordToDelete.toUpperCase())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.message")
                                                .value(String.format("Word [%s] do not exists", wordToDelete)));

                verify(service, times(1)).deleteByWord(wordToDelete);
        }

}