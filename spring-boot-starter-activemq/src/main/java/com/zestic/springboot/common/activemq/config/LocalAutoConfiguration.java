package com.zestic.springboot.common.activemq.config;

import com.zestic.springboot.common.activemq.ActiveMQConsumer;
import com.zestic.springboot.common.activemq.ActiveMQProducer;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ActiveMQProperties.class)
public class LocalAutoConfiguration implements ApplicationContextAware {

    private final ActiveMQProperties properties;
    private ApplicationContext applicationContext;

    public LocalAutoConfiguration(ActiveMQProperties properties) {
        this.properties = properties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ActiveMQConsumer activeMQConsumer() {
        return new ActiveMQConsumer(properties);
    }

    @Bean
    public ActiveMQProducer activeMQProducer() {
        return new ActiveMQProducer(properties);
    }
}
