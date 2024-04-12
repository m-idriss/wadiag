package com.dime.wadiag.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LogModelTest {

    @Test
    @DisplayName("Should create LogModel object with default values")
    void testCreateLogModelWithDefaultValues() {
        // Arrange

        // Act
        LogModel logModel = new LogModel();

        // Assert
        Assertions.assertEquals(0, logModel.getId());
        Assertions.assertNull(logModel.getTopic());
        Assertions.assertEquals(Instant.now().getEpochSecond(), logModel.getDateOfCreation().getEpochSecond());
        Assertions.assertNull(logModel.getContent());
        Assertions.assertNull(logModel.getDetail());
        Assertions.assertEquals(0, logModel.getHttpStatus());
        Assertions.assertNull(logModel.getMessage());
    }

    @Test
    @DisplayName("Should create LogModel object with provided values")
    void testCreateLogModelWithProvidedValues() {
        // Arrange
        long id = 1;
        String topic = "Test Topic";
        Instant dateOfCreation = Instant.parse("2022-01-01T00:00:00Z");
        String content = "Test Content";
        String detail = "Test Detail";
        int httpStatus = 200;
        String message = "Test Message";

        // Act
        LogModel logModel = LogModel.builder()
                .id(id)
                .topic(topic)
                .dateOfCreation(dateOfCreation)
                .content(content)
                .detail(detail)
                .httpStatus(httpStatus)
                .message(message)
                .build();

        // Assert
        Assertions.assertEquals(id, logModel.getId());
        Assertions.assertEquals(topic, logModel.getTopic());
        Assertions.assertEquals(dateOfCreation, logModel.getDateOfCreation());
        Assertions.assertEquals(content, logModel.getContent());
        Assertions.assertEquals(detail, logModel.getDetail());
        Assertions.assertEquals(httpStatus, logModel.getHttpStatus());
        Assertions.assertEquals(message, logModel.getMessage());
    }

    @Test
    @DisplayName("Should create LogModel object with setter")
    void testCreateLogModelWithSetter() {
        // Arrange
        long id = 1;
        String topic = "Test Topic";
        Instant dateOfCreation = Instant.parse("2022-01-01T00:00:00Z");
        String content = "Test Content";
        String detail = "Test Detail";
        int httpStatus = 200;
        String message = "Test Message";

        // Act
        LogModel logModel = new LogModel();

        logModel.setId(id);
        logModel.setTopic(topic);
        logModel.setDateOfCreation(dateOfCreation);
        logModel.setContent(content);
        logModel.setDetail(detail);
        logModel.setHttpStatus(httpStatus);
        logModel.setMessage(message);

        // Assert
        assertEquals(id, logModel.getId());
        assertEquals(topic, logModel.getTopic());
        assertEquals(dateOfCreation, logModel.getDateOfCreation());
        assertEquals(content, logModel.getContent());
        assertEquals(detail, logModel.getDetail());
        assertEquals(httpStatus, logModel.getHttpStatus());
        assertEquals(message, logModel.getMessage());
    }

}