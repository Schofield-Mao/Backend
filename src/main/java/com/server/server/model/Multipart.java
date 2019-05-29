package com.server.server.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("multipart")
public class Multipart {
    long id;
    long momentId;
    String link;
    LocalDateTime createAt;
    LocalDateTime updatedAt;
}
