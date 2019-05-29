package com.server.server.service;

import com.server.server.model.UserContext;
import com.server.server.model.UserRole;
import com.server.server.model.meta.Role;
import com.server.server.security.token.AccessJwtToken;
import com.server.server.security.token.JwtTokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private JwtTokenFactory tokenFactory;

    public AccessJwtToken loginAdmin(long id){
        List<UserRole> roles = Arrays.asList(new UserRole(id,Role.ADMIN));
        List<GrantedAuthority> authorities = roles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());
        UserContext context = UserContext.create("username",authorities,1111111111);
        return tokenFactory.createAccessJwtToken(context);
    }
}
