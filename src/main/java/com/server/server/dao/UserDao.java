package com.server.server.dao;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.server.model.User;
import com.server.server.model.UserRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.mapstruct.Mapper;

import java.sql.JDBCType;
import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("SELECT id,username,nickname,sex FROM users")
    List<User> getAll();

    @Select("SELECT id,username,nickname,sex FROM users WHERE id = #{id}")
    User getById(long id);

    @Insert("INSERT INTO users (id,username,password,sex,nickname,created_at,updated_at) " +
            "VALUES(#{id},#{username},#{password},#{sex},#{nickname},#{createdAt},#{updatedAt})")
    int addUser(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteUser(long id);


    @Select("select id, username, password from users where username = #{username}")
    @Results(
            value = {
                    @Result(id = true, property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.INTEGER),
                    @Result(property = "username", column = "username", javaType = String.class, jdbcType = JdbcType.VARCHAR),
                    @Result(property = "password", column = "password", javaType = String.class, jdbcType = JdbcType.VARCHAR),
                    @Result(property = "roles", column = "id", javaType = List.class, many = @Many(
                            select = "com.server.server.dao.UserRoleDao.getRolesById"
                    ) ),
            }
    )
    User getByUsername(String username);

}

