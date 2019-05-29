package com.server.server;

import com.server.server.dao.UserRoleDao;
import com.server.server.model.User;
import com.server.server.model.UserRole;
import com.server.server.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceTest {
    @Autowired
    UserRoleDao userRoleDao;

    @Test
    public void userdaoTest(){
        UserService userService = new UserService();
        userService.getById(123l);
        //userRoleDao.toString();
    }
}
