package com.dime.wadiag.kafka;

import java.io.IOException;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository repository;

    @Override
    public LogModel save(LogModel log) throws IOException {
        return repository.save(log);
    }

}
