package com.server.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.server.server.Exception.NotFoundException;
import com.server.server.Util.ObjMapper;
import com.server.server.Util.SnowIdWorker;
import com.server.server.Util.Table;
import com.server.server.dao.ReplyDao;
import com.server.server.dto.QueryObj.PageQueryObj;
import com.server.server.dto.QueryObj.ReplyDTO;
import com.server.server.dto.ResponseObj.RichReplyDTO;
import com.server.server.dto.UserDTO;
import com.server.server.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class ReplyService {
    @Autowired
    ReplyDao replyDao;
    @Autowired
    ObjMapper objMapper;
    @Autowired
    SnowIdWorker idWorker;
    @Autowired
    MommentService mommentService;
    @Autowired
    UserService userService;

    public void insertMomentReply(long userId, ReplyDTO replyDTO)throws Exception{

        if(!mommentService.isMomentExist(replyDTO.getParentId())){
            throw new NotFoundException("Moment of id: "+replyDTO.getParentId()+"not found");
        }
        Reply reply = (Reply) ObjMapper.map(replyDTO,Reply.class);
        reply.setId(idWorker.nextId(Table.REPLY));
        reply.setUserId(userId);
        reply.setUpdatedAt(LocalDateTime.now());
        replyDao.insert(reply);
    }

    public PageQueryObj<RichReplyDTO> getReplyByMomentId(IPage<Reply> page, long momentId){
        return  getReplyByParentId(page,momentId);
    }

    public PageQueryObj<RichReplyDTO> getReplyByParentId(IPage<Reply> page, long parentId){
        QueryWrapper<Reply> replyQueryWrapper = new QueryWrapper<>();
        replyQueryWrapper.select("id","content","user_id","created_at").eq("parent_id",parentId);
        IPage<Reply> replyIPage = replyDao.selectPage(page,replyQueryWrapper);
        replyIPage.getRecords().stream().map(e->{
            RichReplyDTO dto = (RichReplyDTO)ObjMapper.map(e,RichReplyDTO.class);
            UserDTO user = userService.getById(dto.getUserId());
            dto.setAvatar(user.getAvatar());
            dto.setNickname(user.getNickname());
            return dto;
        }).collect(Collectors.toList());
        PageQueryObj<RichReplyDTO> pageQueryObj =
                (PageQueryObj)ObjMapper.map(replyIPage,PageQueryObj.class);
        return pageQueryObj;

    }

}
