package com.server.server.dto.ResponseObj;

import com.server.server.dto.QueryObj.PageQueryObj;
import com.server.server.dto.QueryObj.ReplyDTO;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDTO {
    long commentId;
    String content;
    LocalDateTime updatedAt;
    List<PageQueryObj<ReplyDTO>> replyDTOList;
}
