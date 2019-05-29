package com.server.server.dto;

import com.server.server.model.meta.Sex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("userRegisterCmd")
@Data
public class UserRegisterCmd {
    @ApiModelProperty("usernmae")
    String username;
    @ApiModelProperty("password")
    String password;
    @ApiModelProperty("nickname")
    String nickname;
    @ApiModelProperty("sex")
    Sex sex;
}
