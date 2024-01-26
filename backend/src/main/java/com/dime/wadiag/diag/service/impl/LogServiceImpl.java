package com.dime.wadiag.diag.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.dime.wadiag.diag.model.LogData;
import com.dime.wadiag.diag.repository.LogRepository;
import com.dime.wadiag.diag.service.LogService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository repository;

    @Override
    public LogData save(LogData log) throws IOException {
        return repository.save(log);
    }

}
