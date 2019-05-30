package com.server.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.server.server.model.meta.Role;
import com.server.server.model.meta.Sex;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@TableName("users")
public class User {
    @TableId(type = IdType.INPUT)
    private long id;
    private String username;
    private String password;
    private Sex sex;
    private String avatar;
    @TableField(exist = false)
    private List<UserRole> roles;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public User(long id, String username, Sex sex){
        this.id = id;
        this.username = username;
        this.sex = sex;
    }
    public User(){
        this.roles = new ArrayList<>();
    }
    public User(long id, String username, String password){
        this.username = username;
        this.password = password;
        this.id = id;
    }
    public User(String username, String password, List<Role> roles){
        this.username = username;
        this.password = password;
        this.sex = Sex.UNKNOWN;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    public static User create(String username, String password, List<Role> roles){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return new User(username,encoder.encode(password),roles);
    }
    public void addRole(Role role){
        this.roles . add(new UserRole(this.id, role));
    }
}
