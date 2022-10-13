package in.zestic.common.security.provider;

import in.zestic.common.security.interceptors.UserAuthentication;
import in.zestic.common.security.session.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.Resource;

@Component
public class CustomIdentityAuthenticationProvider implements AuthenticationProvider {

    @Resource(name = "session")
    private Session session;
    private final UserAuthentication loader;

    public CustomIdentityAuthenticationProvider(UserAuthentication loader) {
        this.loader = loader;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        session.setUsername(username);
        session.setPassword(password);
        Session session = loader.authenticate(username, password);
        UserDetails userDetails = session.getUserDetails();
        if (userDetails != null) {
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Unauthorized");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType
                .equals(UsernamePasswordAuthenticationToken.class);
    }

    @Bean
    @RequestScope
    public Session session() {
        return new Session();
    }
}
