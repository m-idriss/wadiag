package com.dime.wadiag.diag;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testIndexWordEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/word/"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().string("You're lost !"));
    }

    @Test
    void testLostWordEndpoint() throws Exception {
        String name = "John";
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/word/" + name))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().string("Your word is : " + name));
    }
}

