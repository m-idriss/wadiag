package com.dime.wadiag.kafka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dime.wadiag.model.LogModel;
import com.dime.wadiag.service.LogService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerTest {

    @Mock
    private LogService mockService;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @Test
    void testListenGroup() {
        // Arrange
        String message = "Test.Message.200.Word";

        // Act
        kafkaConsumer.listenGroup(message);

        // Assert
        verify(mockService, times(1)).save(any(LogModel.class));
    }

    @Test
    void testListenGroup_emptyMessage() {
        // Arrange
        String message = "";

        // Act
        kafkaConsumer.listenGroup(message);

        // Assert
        verify(mockService, never()).save(any(LogModel.class));
    }
}
