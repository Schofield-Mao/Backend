package com.server.server.dao;


import com.server.server.model.User;
import org.apache.ibatis.annotations.*;
import org.mapstruct.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserDao {

    @Select("SELECT * FROM users")
    List<User> getAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    User getById(long id);

    @Insert("INSERT INTO users (id,username,password,sex,nickname,role) " +
            "VALUES(#{id},#{username},#{password},#{sex},#{nickname},#{role})")
    int addUser(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteUser(long id);

}

