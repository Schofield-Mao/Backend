package com.server.server.dto.ResponseObj;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RichReplyDTO {
    String content;
    LocalDateTime createdAt;
    String nickname;
    String avatar;
    long userId;
}
