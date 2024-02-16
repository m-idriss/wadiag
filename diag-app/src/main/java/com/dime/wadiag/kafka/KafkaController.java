package com.dime.wadiag.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/kafka")
@AllArgsConstructor
@Tag(name = "kafka", description = "Interact with Queue")
public class KafkaController {

    @Autowired
    private KafkaPublisher kafkaPublisher;

    @PostMapping("publish")
    public ResponseEntity<String> publishMessage(@RequestParam String message) {
        try {
            kafkaPublisher.sendMessage(KafkaConstants.TOPIC, message);
            return ResponseEntity.ok(null);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getLocalizedMessage());
        }
    }

}