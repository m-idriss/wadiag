package com.dime.wadiag.diag.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.dime.wadiag.diag.model.LogData;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import retrofit2.Response;

@Slf4j
@Service
public class CreateLogProducer {

    private final KafkaTemplate<String, LogData> createLogKafkaTemplate;

    private final String createLogTopic;
|
    @Autowired
    private LogService logService;

    public CreateLogProducer(KafkaTemplate<String, LogData> createLogKafkaTemplate,
            @Value("${spring.kafka.log.topic.create-log}") String createLogTopic) {
        this.createLogKafkaTemplate = createLogKafkaTemplate;
        this.createLogTopic = createLogTopic;
    }

    public boolean sendLogEvent(LogData logData) throws ExecutionException, InterruptedException, IOException {
        SendResult<String, LogData> sendResult = createLogKafkaTemplate.send(createLogTopic, logData).get();
        log.info("Create log {} event sent via Kafka", logData);
        log.info(sendResult.toString());
        logService.save(logData);
        return true;
    }

    public <T> void sendLogEvent(Response<T> response, Request request)
            throws ExecutionException, InterruptedException, IOException {
        LogData logData = new LogData();
        logData.setContent(response.message());
        logData.setHttpStatus(response.code());
        logData.setKey(request.url().toString());
        logData.setMessage(response.toString());
        sendLogEvent(logData);
    }
}
