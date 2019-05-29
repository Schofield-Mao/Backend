package com.server.server.security.auth.jwt;

import com.server.server.config.JwtSettings;
import com.server.server.model.UserContext;
import com.server.server.security.token.RawJwtAccessToken;
import com.server.server.security.auth.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtSettings settings;

    @Autowired
    public JwtAuthenticationProvider(JwtSettings settings){
        this.settings = settings;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawJwtAccessToken rawAccessToken = (RawJwtAccessToken) authentication.getCredentials();
        Jws<Claims> claimsJws = rawAccessToken.parseClaims(settings.getTokenSigningKey());
        String subject = claimsJws.getBody().getSubject();
        List<String > scopes = claimsJws.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        long id = claimsJws.getBody().get("current_user_id", Long.class);
        UserContext userContext = UserContext.create(subject,authorities,id);
        return new JwtAuthenticationToken(userContext,userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
