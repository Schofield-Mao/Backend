package com.server.server.Exception;

import org.springframework.security.authentication.AuthenticationServiceException;

import javax.security.auth.message.AuthException;

public class AuthMethodNotSupportedException extends AuthenticationServiceException {
    public AuthMethodNotSupportedException(String msg){
        super(msg);
    }
}
