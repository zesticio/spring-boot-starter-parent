package in.zestic.common.security.adapters;

import in.zestic.common.security.config.AppBasicAuthenticationEntryPoint;
import in.zestic.common.security.provider.CustomIdentityAuthenticationProvider;
import in.zestic.common.security.session.Session;
import in.zestic.common.security.interceptors.GenericServiceInterceptor;
import in.zestic.common.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.annotation.RequestScope;

/**
 * To enable Spring Security web security support. we have extended WebSecurityConfigurerAdapter
 * to override spring features with our custom requirements.Here we want our every request to be
 * authenticated using HTTP Basic authentication. If authentication fails, the configured
 * AuthenticationEntryPoint will be used to retry the authentication process.
 */
@Configuration
@ComponentScan
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/actuator/**"
    };

    private final UserDetailsServiceImpl service;
    private final CustomIdentityAuthenticationProvider authenticationProvider;

    public CustomWebSecurityConfigurerAdapter(UserDetailsServiceImpl service,
                                              CustomIdentityAuthenticationProvider customIdentityAuthenticationProvider,
                                              GenericServiceInterceptor serviceInterceptor) {
        this.service = service;
        this.authenticationProvider = customIdentityAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("**/**").authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(basicAuthenticationEntryPoint());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("**/v2/api-docs")
                .antMatchers("**/configuration/**")
                .antMatchers("**/webjars/**")
                .antMatchers("**/public")
                .antMatchers("**/actuator/**")
                .antMatchers("**/swagger-resources/**")
                .antMatchers("**/swagger-ui.html");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    AppBasicAuthenticationEntryPoint basicAuthenticationEntryPoint() {
        return new AppBasicAuthenticationEntryPoint();
    }

    @Bean
    @RequestScope
    public Session session() {
        return new Session();
    }
}
