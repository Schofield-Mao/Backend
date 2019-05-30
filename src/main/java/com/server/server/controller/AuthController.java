package com.server.server.controller;

import com.server.server.Exception.DuplicateUsernameException;
import com.server.server.dto.UserRegisterCmd;
import com.server.server.model.ApiResponse;
import com.server.server.security.token.AccessJwtToken;
import com.server.server.service.AuthService;
import com.server.server.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @ApiOperation("token")
    @PostMapping("/token")
    public ApiResponse getTokenn(String username, String password){
        ApiResponse res =  new ApiResponse();
        res.setMessage("username:"+username+"password:"+password);
        return res;
    }

    @ApiOperation("adminLogin")
    @PostMapping("/adminLogin")
    public ApiResponse<AccessJwtToken> loginAdmin(String phone){
        AccessJwtToken token = authService.loginAdmin(phone);
        ApiResponse res = new ApiResponse<>();
        res.setData(token);
        return res;
    }

    @ApiOperation("register")
    @PostMapping("/register")
    public ApiResponse register(@RequestBody UserRegisterCmd userRegisterCmd){
        try{
            userService.register(userRegisterCmd);
        }catch (DuplicateUsernameException e){
            ApiResponse ex =  ApiResponse.fail();
            ex.setMessage(e.getMessage());
            return ex;
        }
        return ApiResponse.success();
    }
}
