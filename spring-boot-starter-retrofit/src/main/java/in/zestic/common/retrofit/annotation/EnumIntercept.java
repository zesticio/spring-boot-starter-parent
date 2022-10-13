package in.zestic.common.retrofit.annotation;


import in.zestic.common.retrofit.interceptor.BasePathMatchInterceptor;
import in.zestic.common.retrofit.interceptor.EnvEnum;

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
