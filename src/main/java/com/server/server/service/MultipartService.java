package com.server.server.service;

import com.server.server.Util.IdWorker;
import com.server.server.Util.SnowIdWorker;
import com.server.server.Util.Table;
import com.server.server.dao.MultipartDao;
import com.server.server.dao.ReplyDao;
import com.server.server.model.Multipart;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@Data
public class MultipartService {

    @Autowired
    private MultipartDao multipartDao;
    @Autowired
    private SnowIdWorker idWorker;
    @Autowired
    private FileUploadService fileUploadService;

    public void insertMultipart(long momentId, String url){

        Multipart multipart = new Multipart();
        multipart.setMomentId(momentId);
        multipart.setId(idWorker.nextId(Table.MULTIPART));
        multipart.setUpdatedAt(LocalDateTime.now());
        multipart.setUrl(url);
        multipartDao.insert(multipart);
    }
}
