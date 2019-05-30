package com.server.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

    @ApiOperation("reply comment")
    @PostMapping("/reply")
    public ApiResponse reply(
            @CurrentUserId @ApiParam(hidden = true) long userId, @RequestBody ReplyDTO replyDTO)throws Exception{
        replyService.insertMomentReply(userId,replyDTO);
        return ApiResponse.success();
    }

    @ApiOperation("get reply by mommentId")
    @GetMapping("/reply/{mommentId}")
    ApiResponse<RichReplyDTO> getReplyByMomentId(
            @PathVariable(value = "mommentId")long momentId, PageQueryObj pageQuery){
        PageQueryObj<RichReplyDTO> replys = replyService.getReplyByMomentId(pageQuery, momentId);
        ApiResponse res = new ApiResponse();
        res.setData(replys);
        return res;
    }


}
