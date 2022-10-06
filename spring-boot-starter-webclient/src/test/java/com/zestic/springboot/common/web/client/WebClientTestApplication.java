package com.zestic.springboot.common.web.client;

import com.zestic.springboot.common.web.client.annotation.EnableWebClient;
import com.zestic.springboot.common.web.client.config.ClassPathRetrofitClientScanner;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class WebClientTestApplication {

    private final static Logger logger = LoggerFactory.getLogger(ClassPathRetrofitClientScanner.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(WebClientTestApplication.class, args);
        String[] beans = ctx.getBeanDefinitionNames();
        Arrays.sort(beans);
        for (String bean : beans) {
            logger.info(bean);
        }
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
