package com.zestic.springboot.common.kafka;

import com.zestic.springboot.common.Broker;
import com.zestic.springboot.common.Consumer;
import com.zestic.springboot.common.Producer;

public class KafkaFactory implements Broker {


    @Override
    public Consumer createConsumer() {
        return null;
    }

    @Override
    public Producer createProducer() {
        return null;
    }
}
