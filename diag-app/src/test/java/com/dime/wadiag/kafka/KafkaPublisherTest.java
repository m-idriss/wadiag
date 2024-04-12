package com.dime.wadiag.kafka;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

class KafkaPublisherTest {

    @DisplayName("Should send message with various kafkaTemplate and topicName")
    @Test
    void test_send_message() {
        // Mocking KafkaTemplate
        @SuppressWarnings("unchecked")
        KafkaTemplate<String, String> kafkaTemplateMock = Mockito.mock(KafkaTemplate.class);

        // Creating instance of KafkaPublisher and setting the mocked kafkaTemplate
        KafkaPublisher kafkaPublisher = new KafkaPublisher();
        kafkaPublisher.setKafkaTemplate(kafkaTemplateMock);

        // Testing with blank topicName
        kafkaPublisher.sendMessage("", "Test Message");
        // Verifying that no message is sent when topicName is blank
        verify(kafkaTemplateMock, never()).send(anyString(), anyString());

        // Testing with null kafkaTemplate
        kafkaPublisher.setKafkaTemplate(null);
        kafkaPublisher.sendMessage("testTopic", "Test Message");
        // Verifying that no message is sent when kafkaTemplate is null
        verify(kafkaTemplateMock, never()).send(anyString(), anyString());

        // Testing with non-blank topicName and valid kafkaTemplate
        kafkaPublisher.setKafkaTemplate(kafkaTemplateMock);
        kafkaPublisher.sendMessage("testTopic", "Test Message");
        // Verifying that message is sent when topicName is non-blank and kafkaTemplate
        // is valid
        verify(kafkaTemplateMock, times(1)).send("testTopic", "Test Message");
    }

    @ParameterizedTest
    @CsvSource({
            "'Invalid Kafka topic or KafkaTemplate is null. Message not sent.', '', 'Test message'",
            "'Invalid Kafka topic or KafkaTemplate is null. Message not sent.', ' ', 'Test message'",
            "'Invalid Kafka topic or KafkaTemplate is null. Message not sent.', ' ', ''",
            "'Invalid Kafka topic or KafkaTemplate is null. Message not sent.', '', ''",
            "Message sent to Kafka topic 'wadiag.toto' : Test message, 'wadiag.toto', 'Test message'"
    })
    void test_log_message(String expectedLogMessage, String topicName, String message) {
        String logMessage = sendMessage(topicName, message);
        assertThat(logMessage).isEqualTo(expectedLogMessage);
    }

    private String sendMessage(String topicName, String message) {
        // Arrange
        Logger logger = LoggerFactory.getLogger(KafkaPublisher.class);

        // Create a ListAppender to capture log messages
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // Add the ListAppender to the logger
        ch.qos.logback.classic.Logger loggerImpl = (ch.qos.logback.classic.Logger) logger;
        loggerImpl.addAppender(listAppender);

        KafkaPublisher kafkaPublisher = new KafkaPublisher();
        @SuppressWarnings("unchecked")
        KafkaTemplate<String, String> mockKafkaTemplate = mock(KafkaTemplate.class);
        kafkaPublisher.setKafkaTemplate(mockKafkaTemplate);

        // Act
        kafkaPublisher.sendMessage(topicName, message);

        String logMessage = listAppender.list.stream()
                .map(ILoggingEvent::getFormattedMessage)
                .collect(Collectors.joining());

        // Stop the ListAppender to prevent further modifications
        listAppender.stop();

        return logMessage;
    }

}
