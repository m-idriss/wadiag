package com.dime.wadiag.kafka;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

class KafkaPublisherTest {

    @DisplayName("Should send message with various kafkaTemplate and topicName")
    @ParameterizedTest
    @CsvSource({
            "'Invalid Kafka topic or KafkaTemplate is null. Message not sent.', '', 'Test message',true",
            "'Invalid Kafka topic or KafkaTemplate is null. Message not sent.', ' ', 'Test message',true",
            "'Invalid Kafka topic or KafkaTemplate is null. Message not sent.', ' ', '',true",
            "'Invalid Kafka topic or KafkaTemplate is null. Message not sent.', '', '',true",
            "'Invalid Kafka topic or KafkaTemplate is null. Message not sent.', '', '',false",
            "Error sending message to Kafka topic 'testTopic': Test Exception, 'testTopic', 'Test Message',true",
            "Message sent to Kafka topic 'wadiag.toto' : Test message, 'wadiag.toto', 'Test message',true"

    })
    void test_send_message_and_log(String expectedLogMessage, String topicName, String message,
            Boolean isTemplateNotNull) {
        // Arrange
        Logger logger = LoggerFactory.getLogger(KafkaPublisher.class);
        ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) logger;
        LoggerContext loggerContext = logbackLogger.getLoggerContext();
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(listAppender);

        @SuppressWarnings("unchecked")
        KafkaTemplate<String, String> kafkaTemplateMock = Mockito.mock(KafkaTemplate.class);
        KafkaPublisher kafkaPublisher = new KafkaPublisher();
        if (isTemplateNotNull) {
            kafkaPublisher.setKafkaTemplate(kafkaTemplateMock);
        }
        if (expectedLogMessage.contains("Error")) {
            Exception exception = new RuntimeException("Test Exception");
            when(kafkaTemplateMock.send(topicName, message)).thenThrow(exception);
        }

        // Act
        kafkaPublisher.sendMessage(topicName, message);

        // Verify
        String logMessage = listAppender.list.stream()
                .map(ILoggingEvent::getFormattedMessage)
                .collect(Collectors.joining());
        assertThat(logMessage).isEqualTo(expectedLogMessage);

        if (expectedLogMessage.contains("Message sent") || expectedLogMessage.contains("Error")) {
            verify(kafkaTemplateMock, times(1)).send(topicName, message);
        } else {
            verify(kafkaTemplateMock, never()).send(anyString(), anyString());
        }

        // Clean up
        listAppender.stop();
        loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).detachAppender(listAppender);
    }

}
