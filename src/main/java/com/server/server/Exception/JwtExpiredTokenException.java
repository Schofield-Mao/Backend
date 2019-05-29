package com.server.server.Exception;

import com.server.server.security.token.JwtToken;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;


@Getter
public class JwtExpiredTokenException extends AuthenticationException {

    JwtToken token;

    public JwtExpiredTokenException(String msg){
        super(msg);
    }

}
