package com.dime.wadiag.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("NotificationService")
public class CreateLogConsumer {

    @KafkaListener(topics = "${spring.kafka.log.topic.create-log}", containerFactory = "NotificationContainerFactory")
    public void createLogListener(@Payload LogModel logModel, Acknowledgment ack) {
        log.info("Notification service received lofModel {} ", logModel);
        ack.acknowledge();

    }
}