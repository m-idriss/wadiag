package com.dime.wadiag.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dime.wadiag.model.LogModel;
import com.dime.wadiag.repository.LogRepository;
import com.dime.wadiag.service.LogService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository repository;

    @Override
    public List<LogModel> findAll() {
        return repository.findAll();
    }

    @SuppressWarnings("null")
    @Override
    public LogModel save(LogModel log) {
        return repository.save(log);
    }

    @Override
    public LogModel findById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

}
