package com.zestic.springboot.common.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.JMSException;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Application.class);

    @Autowired
    private TestProducer producer;
    @Autowired
    private TestConsumer consumer;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws JMSException {
        try {
            producer.start();
            consumer.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
