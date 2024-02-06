package com.dime.wadiag.kafka;

import java.io.IOException;

public interface LogService {
    LogModel save(LogModel logModel) throws IOException;

}
