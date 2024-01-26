package com.dime.wadiag.kafka;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import retrofit2.Response;

@Slf4j
@Service
public class CreateLogProducer {

    private final KafkaTemplate<String, LogModel> createLogKafkaTemplate;

    private final String createLogTopic;

    @Autowired
    private LogService logService;

    public CreateLogProducer(KafkaTemplate<String, LogModel> createLogKafkaTemplate,
            @Value("${spring.kafka.log.topic.create-log}") String createLogTopic) {
        this.createLogKafkaTemplate = createLogKafkaTemplate;
        this.createLogTopic = createLogTopic;
    }

    public boolean sendLogEvent(LogModel logModel) throws ExecutionException, InterruptedException, IOException {
        SendResult<String, LogModel> sendResult = createLogKafkaTemplate.send(createLogTopic, logModel).get();
        log.info("Create log {} event sent via Kafka", logModel);
        log.info(sendResult.toString());
        logService.save(logModel);
        return true;
    }

    public <T> void sendLogEvent(Response<T> response, Request request)
            throws ExecutionException, InterruptedException, IOException {
                LogModel logModel = new LogModel();
        logModel.setContent(response.message());
        logModel.setTopic("log");
        logModel.setHttpStatus(response.code());
        logModel.setDetail(request.url().toString());
        logModel.setMessage(response.toString());
        sendLogEvent(logModel);
    }
}
