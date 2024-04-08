package com.dime.wadiag.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dime.wadiag.model.LogModel;
import com.dime.wadiag.repository.LogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogServiceImplTest {

    @Mock
    private LogRepository logRepository;

    @InjectMocks
    private LogServiceImpl logService;

    // Returns a list of LogModel objects when repository has data
    @Test
    void test_returns_list_when_repository_has_data() {
        // Arrange
        List<LogModel> mockLogs = new ArrayList<>();
        when(logRepository.findAll()).thenReturn(mockLogs);

        // Act
        List<LogModel> result = logService.findAll();

        // Assert
        assertEquals(mockLogs, result);
    }

    @Test
    void testSave() {
        // Arrange
        LogModel mockLog = new LogModel();
        when(logRepository.save(any(LogModel.class))).thenReturn(mockLog);

        // Act
        LogModel result = logService.save(mockLog);

        // Assert
        assertEquals(mockLog, result);
    }

    @Test
    void testFindById() {
        // Arrange
        long id = 1L;
        LogModel mockLog = new LogModel();
        when(logRepository.findById(id)).thenReturn(Optional.of(mockLog));

        // Act
        LogModel result = logService.findById(id);

        // Assert
        assertEquals(mockLog, result);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        long id = 1L;
        when(logRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        LogModel result = logService.findById(id);

        // Assert
        assertEquals(null, result);
    }

    @Test
    void testDelete() {
        // Arrange
        long id = 1L;

        // Act
        logService.delete(id);

        // Assert
        verify(logRepository, times(1)).deleteById(id);
    }
}
