package com.dime.wadiag.kafka;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Setter
public class KafkaPublisher {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topicName, String msg) {
        if (StringUtils.isNotBlank(topicName) && kafkaTemplate != null) {
            try {
                kafkaTemplate.send(topicName, msg);
                log.info("Message sent to Kafka topic '{}' : {}", topicName, msg);
            } catch (Exception e) {
                log.error("Error sending message to Kafka topic '{}': {}", topicName, e.getMessage(), e);
            }
        } else {
            log.warn("Invalid Kafka topic or KafkaTemplate is null. Message not sent.");
        }
    }
}