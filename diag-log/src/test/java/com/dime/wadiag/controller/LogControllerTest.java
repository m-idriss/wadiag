package com.dime.wadiag.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dime.wadiag.model.LogModel;
import com.dime.wadiag.service.LogService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LogController.class)
class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogService logService;

    @Test
    void testSave() throws Exception {
        LogModel mockLog = new LogModel();
        when(logService.save(any(LogModel.class))).thenReturn(mockLog);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/logs")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(mockLog.getMessage()));

        verify(logService, times(1)).save(any(LogModel.class));
    }

    @Test
    void testFindById() throws Exception {
        Long id = 1L;
        LogModel mockLog = new LogModel();
        when(logService.findById(id)).thenReturn(mockLog);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/logs/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(mockLog.getMessage()));

        verify(logService, times(1)).findById(id);
    }

    @Test
    void testListAll() throws Exception {
        List<LogModel> mockLogs = new ArrayList<>();
        when(logService.findAll()).thenReturn(mockLogs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/logs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        verify(logService, times(1)).findAll();
    }

    @Test
    void testDeleteById() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/logs/{id}", id))
                .andExpect(status().isNoContent());

        verify(logService, times(1)).delete(id);
    }
}
