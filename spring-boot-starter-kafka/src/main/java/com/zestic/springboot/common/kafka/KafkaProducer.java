package com.zestic.springboot.common.kafka;

import com.zestic.common.entity.Message;
import com.zestic.common.exception.ApplicationRuntimeException;
import com.zestic.springboot.common.Producer;

import java.util.Map;

public class KafkaProducer extends Kafka implements Producer {

    @Override
    public void submit(Message message) throws ApplicationRuntimeException {

    }

    @Override
    public void submit(Message message, Map<String, Object> optional) throws ApplicationRuntimeException {

    }
}
