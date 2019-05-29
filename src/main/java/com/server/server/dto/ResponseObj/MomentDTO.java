package com.server.server.dto.ResponseObj;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MomentDTO {
    String content;
    long id;
    LocalDateTime createdAt;
}
