package io.zestic.security.interceptors;

import io.zestic.security.session.Session;

public interface UserAuthentication {

    Session authenticate(String name, String password) throws RuntimeException;
    Session authenticate(String name) throws RuntimeException;
}
