package com.server.server.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.server.server.Exception.NotFoundException;
import com.server.server.Util.IdWorker;
import com.server.server.Util.ObjMapper;
import com.server.server.Util.SnowIdWorker;
import com.server.server.Util.Table;
import com.server.server.dao.MomentDao;
import com.server.server.dto.QueryObj.PageQueryObj;
import com.server.server.dto.QueryObj.SendMommentObj;
import com.server.server.dto.ResponseObj.MomentDTO;
import com.server.server.dto.ResponseObj.PostMomentResponseDTO;
import com.server.server.dto.ResponseObj.RichMomentDTO;
import com.server.server.dto.UserDTO;
import com.server.server.model.Moment;
import com.server.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
public class MommentService {
    @Autowired
    MomentDao momentDao;
    @Autowired
    SnowIdWorker idWorker;
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    MultipartService multipartService;
    @Autowired
    UserService userService;

    @Transactional
    public PostMomentResponseDTO postMoment(SendMommentObj mommentObj, long userId) throws Exception{
        String[] code_base64 = (mommentObj.getImage_content_base64()).split("\\|");
        Base64.Decoder decoder = Base64.getDecoder();

        //insert moment
        Moment moment = new Moment();
        String content = code_base64[code_base64.length-1];
        long momentId = idWorker.nextId(Table.MOMENT);
        try {
            moment.setContent(new String(decoder.decode(content)));
        }catch (Exception e){
            throw new Exception(e);
        }

        moment.setUserId(userId);
        moment.setId(momentId);
        moment.setUpdatedAt(LocalDateTime.now());
        PostMomentResponseDTO dto = new PostMomentResponseDTO();
        dto.setMomentId(momentId);
        momentDao.insert(moment);
        //insert picture
        for(int i=0;i<code_base64.length-1;i++){
            String url = fileUploadService.imageByteUpload(code_base64[i]);
            dto.addUrl(url);
            multipartService.insertMultipart(momentId,url);
        }
        return dto;
    }

    public PageQueryObj<RichMomentDTO> getMomentByUserId(IPage iPage, long userId){
        QueryWrapper<Moment> momentQueryWrappe = new QueryWrapper<>();
        momentQueryWrappe.select("id","content","created_at").eq("user_id",userId);
        IPage<Moment> momentIPage = momentDao.selectPage(iPage,momentQueryWrappe);
        momentIPage.getRecords()
                .stream()
                .map(e -> ObjMapper.map(e,RichMomentDTO.class))
                .collect(Collectors.toList());
        PageQueryObj<RichMomentDTO> momentDTOPageQueryObj =
                (PageQueryObj)ObjMapper.map(momentIPage,PageQueryObj.class);
        return momentDTOPageQueryObj;
    }

    public MomentDTO getMomentByMomentId(long momentId) throws NotFoundException{
        Moment moment = momentDao.selectById(momentId);
        UserDTO userDTO = userService.getById(moment.getUserId());
        if(moment == null){
            throw new NotFoundException("moment of id: "+momentId);
        }
        MomentDTO momentDTO = (MomentDTO) ObjMapper.map(moment,MomentDTO.class);
        momentDTO.setUserDTO(userDTO);
        return momentDTO;
    }

    public boolean isMomentExist(long momentId){
        if(momentDao.selectById(momentId) == null){
            return false;
        }
        return true;
    }

}
