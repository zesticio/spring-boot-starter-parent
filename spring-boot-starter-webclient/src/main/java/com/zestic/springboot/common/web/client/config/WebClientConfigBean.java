package com.zestic.springboot.common.web.client.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebClientConfigBean {

    private final WebClientProperties properties;

    public WebClientConfigBean(WebClientProperties properties) {
        this.properties = properties;
    }
}
