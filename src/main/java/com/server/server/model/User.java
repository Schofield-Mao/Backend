package com.server.server.model;

import com.server.server.model.meta.Role;
import com.server.server.model.meta.Sex;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.sql.Date;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private Sex sex;
    private Role role;
    private String nickname;
    @ApiParam(hidden = true)
    private Date createdAt;
    @ApiParam(hidden = true)
    private Date updatedAt;
}
