package com.dime.wadiag.kafka;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaConstants {

    public static final String TOPIC = "log_topic";
    public static final String GROUP_ID = "com.dime.wadiag";
}
