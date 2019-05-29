package com.server.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.server.server.dto.QueryObj.PageQueryObj;
import com.server.server.dto.QueryObj.ReplyDTO;
import com.server.server.Util.CurrentUserId;
import com.server.server.model.ApiResponse;
import com.server.server.model.Reply;
import com.server.server.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @ApiOperation("reply comment")
    @PostMapping("/reply/{commentId}")
    public ApiResponse reply(
            @PathVariable(value = "commentId") long commentId, @CurrentUserId @ApiParam(hidden = true) long userId, @RequestBody ReplyDTO replyDTO){
        replyService.reply(userId,commentId,replyDTO);
        return ApiResponse.success();
    }

    @ApiOperation("get reply by commentId")
    @GetMapping("/reply/{commentId}")
    ApiResponse getReplyByMomentId(
            @PathVariable(value = "commentId")long commentId, PageQueryObj pageQuery){
        IPage<Reply> comments = replyService.getReplyByMomentId(pageQuery, commentId);
        ApiResponse res = new ApiResponse();
        res.setData(comments);
        return res;
    }


}
