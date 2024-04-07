package com.dime.wadiag.kafka;

import org.springframework.beans.factory.annotation.Autowired;
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
        String[] messages = message.split("\\.");
        String word = messages[3];
        service.save(LogModel.builder()
                .httpStatus((Integer.parseInt(messages[2])))
                .content(word)
                .message(messages[0] + "." + messages[1])
                .topic(KafkaConstants.TOPIC)
                .detail(KafkaConstants.GROUP_ID)
                .build());
    }
}
