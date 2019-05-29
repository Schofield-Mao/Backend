package com.server.server.security.auth.ajax;


import com.server.server.dao.UserDao;
import com.server.server.model.User;
import com.server.server.model.UserContext;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    private final BCryptPasswordEncoder encoder;

    private final UserDao userDao;

    @Autowired
    public AjaxAuthenticationProvider(UserDao dao, BCryptPasswordEncoder encoder){
        this.userDao = dao;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        User user = userDao.getByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException("User not found: " + username);
        if(!encoder.matches(password,user.getPassword()))
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());
        UserContext userContext = UserContext.create(user.getUsername(), authorities,user.getId());
        return new UsernamePasswordAuthenticationToken(userContext,null,authorities);
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
