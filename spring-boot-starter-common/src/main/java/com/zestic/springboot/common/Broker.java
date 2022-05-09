package com.zestic.springboot.common;

public interface Broker {

    Consumer createConsumer();
    Producer createProducer();
}
