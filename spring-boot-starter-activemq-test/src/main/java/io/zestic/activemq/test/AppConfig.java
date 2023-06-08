package io.zestic.activemq.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${spring.activemq.primary}")
    private String primaryUri;

    @Value("${spring.activemq.secondary}")
    private String secondaryUri;

    @Value("${spring.activemq.username}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;
}
