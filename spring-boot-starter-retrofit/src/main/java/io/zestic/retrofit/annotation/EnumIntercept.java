package io.zestic.retrofit.annotation;


import io.zestic.retrofit.interceptor.BasePathMatchInterceptor;
import io.zestic.retrofit.interceptor.EnvEnum;

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
