package com.dime.wadiag.kafka;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaConstants {

    public static final String TOPIC = "log_topic";
    public static final String GROUP_ID = "com.dime.wadiag";
    public static final Integer NUM_PARTITIONS = 1;
    public static final Short REPLICATION_FACTOR = 1;
}
