package com.server.server.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.server.server.Exception.NotFoundException;
import com.server.server.dto.QueryObj.SendMommentObj;
import com.server.server.dto.ResponseObj.MomentDTO;
import com.server.server.Util.Table;
import com.server.server.Util.IdWorker;
import com.server.server.Util.ObjMapper;
import com.server.server.dao.MomentDao;
import com.server.server.model.Moment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class MommentService {
    @Autowired
    MomentDao momentDao;
    @Autowired
    IdWorker idWorker;
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    MultipartService multipartService;

    public int send_moment(SendMommentObj mommentObj, long userId){
        String url = fileUploadService.imageFileUpload(mommentObj.getMultipartFile());
//        multipartService.
//        Moment moment = (Moment) ObjMapper.map(mommentObj,Moment.class);
//        moment.setUserId(userId);
//        moment.setId(idWorker.nextId(Table.MOMENT));
//        moment.setUpdatedAt(LocalDateTime.now());
//        return momentDao.insert(moment);
        return 1;
    }

    public IPage<Moment> getMomentByUserId(IPage iPage, long userId){
        QueryWrapper<Moment> momentQueryWrappe = new QueryWrapper<>();
        momentQueryWrappe.select("id","content","created_at").eq("user_id",userId);
        IPage<Moment> momentIPage = momentDao.selectPage(iPage,momentQueryWrappe);
        return momentIPage;
    }

    public MomentDTO getMomentByMomentId(long momentId) throws NotFoundException{
        Moment moment = momentDao.selectById(momentId);
        if(moment == null){
            throw new NotFoundException("moment of id: "+momentId);
        }
        return (MomentDTO) ObjMapper.map(moment,MomentDTO.class);
    }

}
