package com.zestic.springboot.common.web.client;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebClientTestApplication.class)
public class TestRestTemplate {

    RestTemplate restTemplate;

    public TestRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createProducts() {
        Product product = new Product();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);
        return restTemplate.exchange(
                "http://localhost:8080/products", HttpMethod.POST, entity, String.class).getBody();
    }
}
