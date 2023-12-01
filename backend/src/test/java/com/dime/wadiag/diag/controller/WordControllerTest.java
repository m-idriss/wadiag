package com.dime.wadiag.diag.controller;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @DisplayName("Should dialog with save word endpoint")
    @Test
    void test_save_word_endpoint() throws Exception {
        String name = faker.lorem().word();
        Word word = new Word(name);

        when(service.save(name)).thenReturn(word);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/words/" + name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(word)));
    }

    @DisplayName("Should retrieve all words")
    @Test
    void test_find_all_words_endpoint() throws Exception {
        // Assuming you have a list of words in your WordService or a database
        List<Word> wordList = Arrays.asList(
                new Word("word1"),
                new Word("word2"),
                new Word("word3"));

        when(service.findAll()).thenReturn(wordList);

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/words"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(wordList)));
    }
}