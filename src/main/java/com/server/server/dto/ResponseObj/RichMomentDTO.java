package com.server.server.dto.ResponseObj;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RichMomentDTO {
    long id;
    String content;
    LocalDateTime createdAt;

}
