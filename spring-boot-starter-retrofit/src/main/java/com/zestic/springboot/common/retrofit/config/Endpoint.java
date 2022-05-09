package com.zestic.springboot.common.retrofit.config;

import lombok.Data;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author deebendukumar
 */
@Data
public class Endpoint {

    private String identity;
    private String baseUrl;
}
