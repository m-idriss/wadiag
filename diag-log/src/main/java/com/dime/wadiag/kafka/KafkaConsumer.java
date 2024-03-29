package com.dime.wadiag.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dime.wadiag.model.LogModel;
import com.dime.wadiag.service.LogService;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaConsumer {

    @Autowired
    private final LogService service;

    @KafkaListener(topics = KafkaConstants.TOPIC, groupId = KafkaConstants.GROUP_ID)
    public void listenGroupFoo(String message) {
        if (StringUtils.isEmpty(message)) {
            return;
        }
        log.info("Received Message in group " + KafkaConstants.GROUP_ID + " => " + message);
        String word = message.split("\\.")[2];
        service.save(LogModel.builder()
                .httpStatus(HttpStatus.OK.value())
                .content(word)
                .message(message.substring(0, message.length() - word.length() - 1))
                .topic(KafkaConstants.TOPIC)
                .detail(KafkaConstants.GROUP_ID)
                .build());
    }
}
