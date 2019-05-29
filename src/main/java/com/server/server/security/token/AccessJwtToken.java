package com.server.server.security.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import lombok.Getter;

@Getter
public class AccessJwtToken implements JwtToken {
    private String rawToken;
    @JsonIgnore
    private Claims claims;

    public AccessJwtToken(String rawToken, Claims clalims){
        this.rawToken =rawToken;
        this.claims = clalims;
    }

    @Override
    public String getToken() {
        return rawToken;
    }
}
