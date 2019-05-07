package com.server.server;

import com.server.server.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.assertj.core.error.ShouldBeAfterYear;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceTest {
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Test
    public void userdaoTest(){
        System.out.println(sqlSessionFactory);
        SqlSession session =  sqlSessionFactory.openSession();
        List<User> users = session.selectList("select * from sys_user");
        for(User user:users){
            System.out.println(user.getId());
        }
    }
}
