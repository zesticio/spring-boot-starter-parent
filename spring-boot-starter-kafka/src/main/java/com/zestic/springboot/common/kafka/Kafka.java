package com.zestic.springboot.common.kafka;

import com.zestic.common.throttling.ThrottleImpl;
import com.zestic.springboot.common.Client;

public class Kafka implements Client {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Kafka.class);

    @Override
    public void create() {

    }

    @Override
    public void close() {

    }
}
