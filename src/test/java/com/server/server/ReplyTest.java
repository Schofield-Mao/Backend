package com.server.server;

import com.server.server.dto.QueryObj.ReplyDTO;
import com.server.server.service.ReplyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ReplyTest {
    @Autowired
    ReplyService replyService;

    @Test
    public void replyTest(){
        ReplyDTO replyDTO = new ReplyDTO();
    }
}
