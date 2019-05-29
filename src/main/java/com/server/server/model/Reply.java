package com.server.server.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@TableName("reply")
public class Reply {
    long id;
    long userId;
    long parentId;
    String content;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
