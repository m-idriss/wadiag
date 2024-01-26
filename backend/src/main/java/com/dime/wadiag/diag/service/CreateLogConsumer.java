package com.dime.wadiag.diag.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.dime.wadiag.diag.model.LogData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("NotificationService")
public class CreateLogConsumer {

    @KafkaListener(topics = "${spring.kafka.log.topic.create-log}", containerFactory = "NotificationContainerFactory")
    public void createLogListener(@Payload LogData logData, Acknowledgment ack) {
        log.info("Notification service received lofData {} ", logData);
        ack.acknowledge();

    }
}