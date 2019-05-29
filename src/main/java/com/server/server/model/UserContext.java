package com.server.server.model;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
@Getter
public class UserContext {
    private final String username;
    private final long id;
    private final List<GrantedAuthority> authorities;
    private UserContext(String username, List<GrantedAuthority> authorities, long id){
        this.username = username;
        this.authorities = authorities;
        this.id = id;
    }

    public static UserContext create(String username, List<GrantedAuthority> authorities, long id){
        if(StringUtils.isBlank(username))
            throw new IllegalArgumentException("username is blank");
        return new UserContext(username,authorities,id);
    }
}
