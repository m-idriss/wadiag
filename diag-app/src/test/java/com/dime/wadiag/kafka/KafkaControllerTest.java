package com.dime.wadiag.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class KafkaControllerTest {
    // Successfully publish a message to Kafka queue
    @Test
    void test_publishMessage_success() {
        // Arrange
        KafkaPublisher mockKafkaPublisher = mock(KafkaPublisher.class);
        KafkaController kafkaController = new KafkaController(mockKafkaPublisher);
        String message = "Test message";

        // Act
        ResponseEntity<String> response = kafkaController.publishMessage(message);

        // Assert
        verify(mockKafkaPublisher, times(1)).sendMessage(KafkaConstants.TOPIC, message);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
}
