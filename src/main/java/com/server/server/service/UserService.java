package com.server.server.service;

import com.server.server.Exception.ApiException;
import com.server.server.Exception.DuplicateUsernameException;
import com.server.server.Util.Table;
import com.server.server.Util.ObjMapper;
import com.server.server.Util.SnowIdWorker;
import com.server.server.dao.UserDao;
import com.server.server.dao.UserRoleDao;
import com.server.server.dto.UserDTO;
import com.server.server.dto.UserDtoList;
import com.server.server.dto.UserRegisterCmd;
import com.server.server.model.ApiResponse;
import com.server.server.model.User;
import com.server.server.model.meta.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private SnowIdWorker idWorker;
    @Autowired
    private ObjMapper mapper;
    @Autowired
    BCryptPasswordEncoder encoder;

    public ApiResponse<UserDtoList> getAllUsers() {
        ApiResponse res = new ApiResponse();
        List<User> users = userDao.getAll();
        UserDtoList userDtoList = new UserDtoList();
        userDtoList.setUserDTOList((List)users.stream()
                .map(u -> mapper.map(u,UserDTO.class)).collect(Collectors.toList()));
        res.setData(userDtoList);
        if(users == null){
            res.setMessage("no users in system");
        }
        return res;
    }

    public UserDTO getById(long id){
        User user = userDao.getById(id);
        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }
        UserDTO userDTO = (UserDTO) mapper.map(user,UserDTO.class);
        return userDTO;
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

    public ApiResponse<User> getByUsername(String username){
        ApiResponse res = new ApiResponse();
        User user = userDao.getByUsername(username);
        res.setData(user);
        if(user == null){
            res.setMessage("user not found");
        }
        return res;
    }

    public void register(UserRegisterCmd userRegisterCmd) throws DuplicateUsernameException {
        if(userDao.getByUsername(userRegisterCmd.getUsername()) != null){
            throw new DuplicateUsernameException("user name : "+userRegisterCmd.getUsername() +" exists");
        }

        User user = (User) mapper.map(userRegisterCmd, User.class);
        user.setId(idWorker.nextId(Table.USER));
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.addRole(Role.PREMIUM_MEMBER);
        userDao.addUser(user);
        userRoleDao.addUserRole(user.getRoles());
    }

}
