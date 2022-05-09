package com.zestic.springboot.common.retrofit.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpInterceptors {

    HttpInterceptor[] value() default {
    };
}
