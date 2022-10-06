package com.zestic.springboot.common.retrofit.annotation;


import com.zestic.springboot.common.retrofit.interceptor.BasePathMatchInterceptor;
import com.zestic.springboot.common.retrofit.interceptor.EnvEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@InterceptMark
public @interface EnumIntercept {


    EnvEnum envEnum();

    String[] include() default {"/**"};

    String[] exclude() default {};

    Class<? extends BasePathMatchInterceptor> handler() default EnumInterceptor.class;
}
