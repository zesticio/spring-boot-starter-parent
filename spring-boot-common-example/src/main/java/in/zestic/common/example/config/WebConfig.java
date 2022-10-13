package in.zestic.common.example.config;

import in.zestic.common.example.interceptor.UserSecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public WebConfig() {
    }

    @Bean
    public UserSecurityInterceptor getSecurityInterceptor() {
        return new UserSecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSecurityInterceptor()).addPathPatterns("/**");
    }
}
