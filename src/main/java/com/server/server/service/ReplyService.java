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
import java.util.List;
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

    public void inserReply(long userId, ReplyDTO replyDTO){
        Reply reply = (Reply) ObjMapper.map(replyDTO,Reply.class);
        reply.setId(idWorker.nextId(Table.REPLY));
        reply.setUserId(userId);
        reply.setUpdatedAt(LocalDateTime.now());
        replyDao.insert(reply);
    }

    public void insertMomentReply(long userId, ReplyDTO replyDTO)throws Exception{
        if(!mommentService.isMomentExist(replyDTO.getParentId())){
            throw new NotFoundException("Moment of id: "+replyDTO.getParentId()+"not found");
        }
       inserReply(userId,replyDTO);
    }

    public void insertReplyReply(long userId, ReplyDTO replyDTO)throws Exception{

        if(!isReplyExist(replyDTO.getParentId())){
            throw new NotFoundException("Reply of id: "+replyDTO.getParentId()+"not found");
        }
       inserReply(userId, replyDTO);
    }

    public PageQueryObj<RichReplyDTO> getReplyByMomentId(IPage<Reply> page, long momentId)throws Exception{
        if(!mommentService.isMomentExist(momentId)){
            throw new NotFoundException("Moment of id: "+momentId+"not found");
        }
        return  getReplyByParentId(page,momentId);
    }

    public PageQueryObj<RichReplyDTO> getReplyByReplyId(IPage<Reply> page, long replyId)throws Exception{
        if(!isReplyExist(replyId)){
            throw new NotFoundException("Reply of id: "+replyId+"not found");
        }
        return  getReplyByParentId(page,replyId);
    }

    public PageQueryObj<RichReplyDTO> getReplyByParentId(IPage<Reply> page, long parentId){
        QueryWrapper<Reply> replyQueryWrapper = new QueryWrapper<>();
        replyQueryWrapper.select("id","content","user_id","created_at").eq("parent_id",parentId);
        IPage<Reply> replyIPage = replyDao.selectPage(page,replyQueryWrapper);
        List<RichReplyDTO> richReplyDTOList = replyIPage.getRecords().stream().map(e->{
            RichReplyDTO dto = (RichReplyDTO)ObjMapper.map(e,RichReplyDTO.class);
            UserDTO user = userService.getById(dto.getUserId());
            dto.setAvatar(user.getAvatar());
            dto.setNickname(user.getNickname());
            return dto;
        }).collect(Collectors.toList());
        PageQueryObj<RichReplyDTO> pageQueryObj =
                (PageQueryObj)ObjMapper.map(replyIPage,PageQueryObj.class);
        pageQueryObj.setRecords(richReplyDTOList);
        return pageQueryObj;

    }

    public boolean isReplyExist(long replyId){
        if(replyDao.selectById(replyId)==null){
            return false;
        }
        return true;
    }

}
