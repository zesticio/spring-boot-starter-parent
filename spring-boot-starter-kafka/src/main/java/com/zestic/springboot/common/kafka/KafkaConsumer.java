package com.zestic.springboot.common.kafka;

import com.zestic.common.entity.Message;
import com.zestic.springboot.common.Consumer;

public class KafkaConsumer extends Kafka implements Consumer {

    @Override
    public Message receive() {
        return null;
    }
}
