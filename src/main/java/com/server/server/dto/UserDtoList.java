package com.server.server.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDtoList {
    private List<UserDTO> userDTOList;
    public UserDtoList(){
        userDTOList = new ArrayList<>();
    }
}
