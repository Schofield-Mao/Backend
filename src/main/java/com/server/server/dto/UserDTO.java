package com.server.server.dto;

import com.server.server.model.meta.Role;
import com.server.server.model.meta.Sex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("user response model")
public class UserDTO {
    @ApiModelProperty("user id")
    long id;
    @ApiModelProperty("username")
    String username;
    @ApiModelProperty("nickname")
    String nickname;
    @ApiModelProperty("usex")
    Sex sex;
}
