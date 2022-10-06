package com.zestic.springboot.common.web.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class APIClient {

    private boolean debugging = false;
    private HttpHeaders defaultHeaders = new HttpHeaders();

}
