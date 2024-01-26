package com.dime.wadiag.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

//https://docs.codenow.com/java-spring-boot-complex-examples/java-spring-boot-kafka-producer-consumer

@EnableKafka
@Configuration("NotificationConfiguration")
public class CreateLogConsumerConfig {

    @Value("${spring.kafka.log.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.log.consumer.group-id.notification}")
    private String groupId;

    @Bean("NotificationConsumerFactory")
    public ConsumerFactory<String, LogModel> createLogConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(LogModel.class));
    }

    @Bean("NotificationContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, LogModel> createLogKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LogModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createLogConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}
