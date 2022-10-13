package in.zestic.common.retrofit.annotation;


import in.zestic.common.retrofit.interceptor.BasePathMatchInterceptor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@InterceptMark
@Repeatable(Intercepts.class)
public @interface Intercept {
    /**
     * Interceptor matching path pattern
     */
    String[] include() default {"/**"};

    /**
     * Interceptor excludes matching, excludes specified path interception
     */
    String[] exclude() default {};

    /**
     * Interceptor handler
     * First obtain the corresponding Bean from the spring container, if not, use reflection to create one!
     */
    Class<? extends BasePathMatchInterceptor> handler();
}
