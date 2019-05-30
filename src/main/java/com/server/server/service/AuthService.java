package com.server.server.service;

import com.server.server.Util.SnowIdWorker;
import com.server.server.Util.Table;
import com.server.server.dao.UserDao;
import com.server.server.dto.UserDTO;
import com.server.server.model.User;
import com.server.server.model.UserContext;
import com.server.server.model.UserRole;
import com.server.server.model.meta.Role;
import com.server.server.security.token.AccessJwtToken;
import com.server.server.security.token.JwtTokenFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Configuration
@ConfigurationProperties("admin")
@Data
public class AuthService {
    @Autowired
    private JwtTokenFactory tokenFactory;
    private String phone;
    @Autowired
    private SnowIdWorker idWorker;
    @Autowired
    private UserDao userDao;

    public AccessJwtToken loginAdmin(String adminPhone){
        if(!adminPhone.equals(phone))
            throw new UsernameNotFoundException("admin of "+adminPhone+" not found");
        User user = new User();
        user.setPassword(adminPhone);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUsername(adminPhone);
        user.setId(idWorker.nextId(Table.USER));
        List<UserRole> roles = Arrays.asList(new UserRole(user.getId(),Role.ADMIN));
        user.setRoles(roles);
        userDao.insert(user);

        List<GrantedAuthority> authorities = roles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());
        UserContext context = UserContext.create(user.getUsername(),authorities,user.getId());
        return tokenFactory.createAccessJwtToken(context);
    }
}
