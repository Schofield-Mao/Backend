package com.server.server.dto.ResponseObj;

import com.server.server.dto.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MomentDTO {
    UserDTO userDTO;
    String content;
    long id;
    LocalDateTime createdAt;
}
