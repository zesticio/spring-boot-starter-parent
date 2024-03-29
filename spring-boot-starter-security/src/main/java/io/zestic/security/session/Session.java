package io.zestic.security.session;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class Session<T> {

    private String userId;
    private String username;
    private String password;
    private UserDetails userDetails;
    private T client;

    private Integer errorCode;
    private String errorMessage;
}
