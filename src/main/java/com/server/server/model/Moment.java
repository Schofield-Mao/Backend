package com.server.server.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@TableName("moment")
public class Moment {
    long id;
    long userId;
    String content;
    int media;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
