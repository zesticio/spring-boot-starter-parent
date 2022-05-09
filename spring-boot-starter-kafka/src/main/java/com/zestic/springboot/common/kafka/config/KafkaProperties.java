package com.zestic.springboot.common.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.activemq")
public class KafkaProperties {

    private String primary = "tcp://127.0.0.1:61616";
    private String secondary = "tcp://127.0.0.1:61616";
    private String username = "admin";
    private String password = "admin";

    private Boolean useTransaction = false;

    private ConsumerProperties consumer;
    private ProducerProperties producer;
}
