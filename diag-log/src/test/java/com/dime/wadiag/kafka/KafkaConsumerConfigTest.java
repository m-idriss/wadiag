package com.dime.wadiag.kafka;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka
class KafkaConsumerConfigTest {

    @Autowired
    private KafkaConsumerConfig kafkaConsumerConfig;

    @BeforeEach
    void setUp() {
        // Set up any necessary configurations or mocks before each test
    }

    @DisplayName("Should create consumer factory with correct properties")
    @Test
    void test_consumer_factory() {
        // Arrange
        Map<String, Object> expectedProps = new HashMap<>();
        expectedProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerConfig.getBootstrapAddress());
        expectedProps.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.GROUP_ID);
        expectedProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        expectedProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Act
        ConsumerFactory<String, String> consumerFactory = kafkaConsumerConfig.consumerFactory();

        // Assert
        assertThat(consumerFactory.getConfigurationProperties()).isEqualTo(expectedProps);
    }

    @DisplayName("Should create Kafka listener container factory with correct consumer factory")
    @Test
    void test_kafka_listener_container_factory() {
        // Arrange
        @SuppressWarnings("unchecked")
        ConsumerFactory<String, String> mockConsumerFactory = mock(ConsumerFactory.class);
        // mock method call
        when(mockConsumerFactory.getConfigurationProperties()).thenReturn(new HashMap<>());

        // Act
        ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory = kafkaConsumerConfig
                .kafkaListenerContainerFactory();
        kafkaListenerContainerFactory.setConsumerFactory(mockConsumerFactory);

        // Assert
        assertThat(kafkaListenerContainerFactory.getConsumerFactory()).isEqualTo(mockConsumerFactory);
    }
}