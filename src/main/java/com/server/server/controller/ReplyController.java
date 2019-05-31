package com.server.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.server.server.Exception.NotFoundException;
import com.server.server.dto.QueryObj.PageQueryObj;
import com.server.server.dto.QueryObj.ReplyDTO;
import com.server.server.Util.CurrentUserId;
import com.server.server.dto.ResponseObj.RichReplyDTO;
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

    @ApiOperation("reply moment")
    @PostMapping("/moment_reply")
    public ApiResponse replyMoment(
            @CurrentUserId @ApiParam(hidden = true) long userId, @RequestBody ReplyDTO replyDTO)throws Exception{
        replyService.insertMomentReply(userId,replyDTO);
        return ApiResponse.success();
    }

    @ApiOperation("reply reply")
    @PostMapping("/reply_reply")
    public ApiResponse replyReply(
            @CurrentUserId @ApiParam(hidden = true) long userId, @RequestBody ReplyDTO replyDTO)throws Exception{
        replyService.insertReplyReply(userId,replyDTO);
        return ApiResponse.success();
    }

    @ApiOperation("get reply by parentId")
    @GetMapping("/moment_reply")
    ApiResponse<RichReplyDTO> getReplyByMomentId(long replyParentId, PageQueryObj pageQuery)throws Exception{
        PageQueryObj<RichReplyDTO> replys = replyService.getReplyByMomentId(pageQuery, replyParentId);
        ApiResponse res = new ApiResponse();
        res.setData(replys);
        return res;
    }

    @ApiOperation("get reply by parentId")
    @GetMapping("/reply_reply")
    ApiResponse<RichReplyDTO> getReplyByReplyId(long replyParentId, PageQueryObj pageQuery)throws Exception{
        PageQueryObj<RichReplyDTO> replys = replyService.getReplyByReplyId(pageQuery, replyParentId);
        ApiResponse res = new ApiResponse();
        res.setData(replys);
        return res;
    }



}
