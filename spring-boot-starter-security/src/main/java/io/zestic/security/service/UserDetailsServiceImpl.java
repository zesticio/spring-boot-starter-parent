package io.zestic.security.service;

import io.zestic.security.session.Session;
import io.zestic.security.interceptors.UserAuthentication;
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
