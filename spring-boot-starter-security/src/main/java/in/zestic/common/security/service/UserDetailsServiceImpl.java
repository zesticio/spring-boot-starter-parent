package in.zestic.common.security.service;

import in.zestic.common.security.session.Session;
import in.zestic.common.security.interceptors.UserAuthentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAuthentication loader;

    public UserDetailsServiceImpl(UserAuthentication loader) {
        this.loader = loader;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Session session = loader.authenticate(username);
        return session.getUserDetails();
    }
}
