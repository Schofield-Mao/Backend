package com.server.server;

import com.server.server.service.MultipartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;




@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class MultipartServiceTest {

    @Autowired
    MultipartService multipartService;

    @Test
    public void insertTest(){

        //MultipartFile file = new MockMultipartFile("12312312",bytes);
        //multipartService.insertMultipart(12312312l,file);
    }

}
