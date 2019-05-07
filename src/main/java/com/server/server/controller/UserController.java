package com.server.server.controller;

import com.server.server.Exception.ApiException;
import com.server.server.model.ApiResponse;
import com.server.server.model.meta.Sex;
import com.server.server.model.User;
import com.server.server.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ApiResponse<List<User>> users(){
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ApiResponse<User> user(@PathVariable(value="id") long id){
        return userService.getById(id);
    }

    @PutMapping("/user")
    public ApiResponse user(User user){
        return userService.addUser(user);
    }

    @DeleteMapping("/user/{id}")
    public ApiResponse delUser(@PathVariable(value="id") long id) throws ApiException {
        return userService.deleteUser(id);
    }


}
