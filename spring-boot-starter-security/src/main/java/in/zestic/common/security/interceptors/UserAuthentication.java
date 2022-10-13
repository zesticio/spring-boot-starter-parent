package in.zestic.common.security.interceptors;

import in.zestic.common.security.session.Session;

public interface UserAuthentication {

    Session authenticate(String name, String password) throws RuntimeException;
    Session authenticate(String name) throws RuntimeException;
}
