package com.zestic.springboot.common.activemq;

import com.zestic.springboot.common.Broker;
import com.zestic.springboot.common.Consumer;
import com.zestic.springboot.common.Producer;
import com.zestic.springboot.common.activemq.config.ActiveMQProperties;

public class ActiveMQFactory implements Broker {

    private ActiveMQProperties properties;

    public ActiveMQFactory(ActiveMQProperties properties) {
        this.properties = properties;
    }

    @Override
    public Consumer createConsumer() {
        return new ActiveMQConsumer(this.properties);
    }

    @Override
    public Producer createProducer() {
        return new ActiveMQProducer(this.properties);
    }
}
