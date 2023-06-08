package io.zestic.security.adapters;

import io.zestic.security.interceptors.GenericServiceInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@ComponentScan
public class CustomWebMvcConfigurerAdapter implements WebMvcConfigurer {

    private final GenericServiceInterceptor serviceInterceptor;

    public CustomWebMvcConfigurerAdapter(GenericServiceInterceptor serviceInterceptor) {
        this.serviceInterceptor = serviceInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(serviceInterceptor);
    }
}
