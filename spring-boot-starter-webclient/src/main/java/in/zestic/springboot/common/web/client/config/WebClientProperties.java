package in.zestic.springboot.common.web.client.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "web.client")
public class WebClientProperties {

    private static final String DEFAULT_POOL = "default";

}
