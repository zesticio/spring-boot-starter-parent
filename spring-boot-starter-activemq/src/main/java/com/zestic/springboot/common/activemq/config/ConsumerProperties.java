package com.zestic.springboot.common.activemq.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Data
public class ConsumerProperties {

    private String queueName;
    private Integer prefetchLimit = 128;
}
