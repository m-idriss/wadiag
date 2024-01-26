package com.dime.wadiag.diag.service;

import java.io.IOException;

import com.dime.wadiag.diag.model.LogData;

public interface LogService {
    LogData save(LogData log) throws IOException;

}
