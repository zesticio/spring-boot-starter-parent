package com.zestic.springboot.common.activemq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
//@PropertySource("classpath:activemq.properties")
@ConfigurationProperties(prefix = "spring.activemq")
public class ActiveMQProperties {

    private String primary = "tcp://127.0.0.1:61616";
    private String secondary = "tcp://127.0.0.1:61616";
    private String username = "admin";
    private String password = "admin";

    private Boolean useTransaction = false;

    private ConsumerProperties consumer;
    private ProducerProperties producer;
}
