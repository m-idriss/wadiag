package com.dime.wadiag.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dime.wadiag.model.LogModel;

@Service
public interface LogService {
    LogModel save(LogModel log);

    List<LogModel> findAll();

    LogModel findById(long id);

    void delete(long id);

}
