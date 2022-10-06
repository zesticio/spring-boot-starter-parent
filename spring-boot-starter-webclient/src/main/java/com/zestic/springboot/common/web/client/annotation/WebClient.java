package com.zestic.springboot.common.web.client.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebClient {

    /**
     * An absolute URL (the protocol is necessary).
     * Can be specified as property key, eg: ${propertyKey}.
     * If baseUrl is not configured, you must configure serviceId and path optional configuration.
     *
     * @return baseUrl
     */
    String baseUrl() default "";

    /**
     * The name of the service.
     * Can be specified as property key, eg: ${propertyKey}.
     */
    String serviceId() default "";

    /**
     * Path prefix to be used by all method-level mappings.
     */
    String path() default "";

    /**
     * Whether to enable log printing for the current interface
     *
     * @return enableLog
     */
    boolean enableLog() default true;
}
