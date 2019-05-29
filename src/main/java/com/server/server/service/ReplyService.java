package com.server.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.server.server.dto.QueryObj.ReplyDTO;
import com.server.server.Util.Table;
import com.server.server.Util.ObjMapper;
import com.server.server.Util.SnowIdWorker;
import com.server.server.dao.ReplyDao;
import com.server.server.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReplyService {
    @Autowired
    ReplyDao replyDao;
    @Autowired
    ObjMapper objMapper;
    @Autowired
    SnowIdWorker idWorker;

    public void reply(long userId, long commentId, ReplyDTO replyDTO){
        Reply reply = (Reply) ObjMapper.map(replyDTO,Reply.class);
        reply.setId(idWorker.nextId(Table.REPLY));
        reply.setUserId(userId);

        reply.setUpdatedAt(LocalDateTime.now());
        replyDao.insert(reply);
    }

    public IPage<Reply> getReplyByMomentId(IPage<Reply> page, long commentId){
        QueryWrapper<Reply> replyQueryWrapper = new QueryWrapper<>();
        replyQueryWrapper.select("id","content","updated_at").eq("comment_id",commentId);
        return  replyDao.selectPage(page,replyQueryWrapper);
    }

}
