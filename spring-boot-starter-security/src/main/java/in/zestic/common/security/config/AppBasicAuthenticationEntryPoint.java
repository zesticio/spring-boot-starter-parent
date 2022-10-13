package in.zestic.common.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class will be responsible to send response when the credentials are no longer authorized. If the authentication event was successful, or authentication was not attempted because the HTTP header did not contain a supported authentication request, the filter chain will continue as normal. The only time the filter chain will be interrupted is if authentication fails and the AuthenticationEntryPoint is called.
 */
@Component
public class AppBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException {
        resolver.resolveException(request, response, null, authEx);
//        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        PrintWriter writer = response.getWriter();
//        writer.println("HTTP Status 401 - " + authEx.getMessage());
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("DeveloperStack");
        super.afterPropertiesSet();
    }
}
