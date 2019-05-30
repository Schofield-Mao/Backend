package com.server.server.controller;

import com.server.server.Exception.ApiException;
import com.server.server.dto.UserDTO;
import com.server.server.dto.UserDtoList;
import com.server.server.dto.UserRegisterCmd;
import com.server.server.model.ApiResponse;
import com.server.server.model.User;
import com.server.server.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("get all user")
    @GetMapping("/users")
    public ApiResponse<UserDtoList> users(){
         return userService.getAllUsers();
    }

    @ApiOperation("get user by id")
    @GetMapping("/user/{id}")
    public ApiResponse<UserDTO> getUserById(@PathVariable(value="id") long id){
        ApiResponse res = new ApiResponse();
        UserDTO userDTO =  userService.getById(id);
        res.setData(userDTO);
        return res;
    }

    @ApiOperation("delete user by id")
    @DeleteMapping("/user/{id}")
    public ApiResponse delUser(@PathVariable(value="id") long id) throws ApiException {
        return userService.deleteUser(id);
    }



}
