package com.server.server.service;

import com.server.server.Exception.ApiException;
import com.server.server.dao.UserDao;
import com.server.server.model.ApiResponse;
import com.server.server.model.User;
import io.swagger.annotations.Api;
import javassist.bytecode.stackmap.BasicBlock;
import net.bytebuddy.asm.Advice;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public ApiResponse<List<User>> getAllUsers() {
        ApiResponse res = new ApiResponse();
        List<User> users = userDao.getAll();
        res.setData(users);
        if(users == null){
            res.setMessage("not users in system");
        }
        return res;
    }

    public ApiResponse<User> getById(long id){
        ApiResponse res = new ApiResponse();
        User user = userDao.getById(id);
        res.setData(user);
        if(user == null){
            res.setMessage("user not found");
        }
        return res;
    }

    public ApiResponse addUser(User user){
        ApiResponse res =  new ApiResponse();
        if(getById(user.getId()) != null){
            res.setMessage("user already exisits");
            return res;
        }
        if(userDao.addUser(user) == 1){
            res.setMessage("add user success");
        }else{
            res.setMessage("add user fail");
        }
        return res;
    }

    public ApiResponse deleteUser(long id) throws ApiException {
        ApiResponse res =  new ApiResponse();
        if(userDao.deleteUser(id) == 1){
            res.setMessage("delete user success");
        }else{
            res.setMessage("delete user fail");
        }
        return res;
    }
}
