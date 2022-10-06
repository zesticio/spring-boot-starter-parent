package in.zestic.springboot.common.web.client.annotation;

import in.zestic.springboot.common.web.client.config.WebClientConfigBean;
import in.zestic.springboot.common.web.client.config.WebClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WebClientProperties.class)
@AutoConfigureAfter({JacksonAutoConfiguration.class})
public class WebClientAutoConfiguration implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(WebClientAutoConfiguration.class);

    private WebClientProperties properties;
    private ApplicationContext applicationContext;

    public WebClientAutoConfiguration(WebClientProperties properties) {
        this.properties = properties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    @ConditionalOnMissingBean
    public WebClientConfigBean webClientConfigBean() throws IllegalAccessException, InstantiationException {
        WebClientConfigBean webClientConfigBean = new WebClientConfigBean(properties);
        return webClientConfigBean;
    }
}
