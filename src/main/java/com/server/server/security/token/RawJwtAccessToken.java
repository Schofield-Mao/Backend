package com.server.server.security.token;

import com.server.server.Exception.JwtExpiredTokenException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;


public class RawJwtAccessToken implements JwtToken {
    private static Logger logger = LoggerFactory.getLogger(RawJwtAccessToken.class);

    private String token;

    public RawJwtAccessToken(String token){
        this.token = token;
    }

    public Jws<Claims> parseClaims(String signingKey){
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
        }catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex){
            logger.error("Invalid JWT Token", ex);
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        }catch (ExpiredJwtException e){
            logger.info("JWT Token is expired", e);
            throw new JwtExpiredTokenException("JWT Token expired");
        }

    }

    @Override
    public String getToken() {
        return token;
    }
}
