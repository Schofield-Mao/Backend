package com.server.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.server.model.User;
import com.server.server.model.UserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.net.PortUnreachableException;
import java.util.List;

public interface UserRoleDao{
    @Select("SELECT user_id as userId,role FROM user_role where user_id = #{userId}")
    List<UserRole> getRolesById(long userId);

    @Insert({
            "<script>",
            "insert into user_role(user_id, role) values ",
            "<foreach collection='userRoles' item='item' index='index' separator=','>",
            "(#{item.userId}, #{item.role})",
            "</foreach>",
            "</script>"
            })
    int addUserRole(@Param(value="userRoles" ) List<UserRole> userRoles);
}
