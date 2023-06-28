package io.zestic.activemq.config;

import io.zestic.activemq.ActiveMQProducer;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@PropertySource("classpath:producer.properties")
public class ActiveMQProducerConfiguration {

    @Value("${spring.activemq.primary-uri}")
    private String primaryUri;

    @Value("${spring.activemq.secondary-uri}")
    private String secondaryUri;

    @Value("${spring.activemq.username}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${spring.activemq.queue.name}")
    private String queueName;

    @Value("${spring.activemq.prefetch-limit}")
    private String prefetchLimit;

    @Value("${spring.activemq.throughput}")
    private Integer throughput;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ActiveMQProducer producer() {
        ActiveMQProducer producer = new ActiveMQProducer.Builder()
                .primaryUri(primaryUri)
                .secondaryUri(secondaryUri)
                .username(username)
                .password(password)
                .queueName(queueName)
                .throughput(throughput)
                .build();
        return producer;
    }
}
