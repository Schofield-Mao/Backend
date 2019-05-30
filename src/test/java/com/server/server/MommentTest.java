package com.server.server;

import com.server.server.dto.QueryObj.SendMommentObj;
import com.server.server.service.MommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class MommentTest {
    @Test
    public void base64Test(){
        File file = new File("/home/mjh/Pictures/test.jpg");
        try{

            BufferedImage bi = ImageIO.read(file);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg",bytes);
            byte[] image = bytes.toByteArray();
            Base64.Encoder encoder = Base64.getEncoder();
            Base64.Decoder decoder = Base64.getDecoder();
            String code = encoder.encodeToString(image);
            byte[] im = decoder.decode(code);
            File file1 = new File("/home/mjh/Pictures/newimage.jpg");
            if(!file1.exists()){
                file1.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(file1);
            BufferedOutputStream bs = new BufferedOutputStream(fs);
            bs.write(im,0, im.length);
            System.out.println(code);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void imageAndContentEncoder() throws IOException{
        String content = "HELLO WORLD";
        File file = new File("/home/mjh/Pictures/test.jpg");
        BufferedImage bi = ImageIO.read(file);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg",bytes);
        byte[] image = bytes.toByteArray();
        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();
        String image_code = encoder.encodeToString(image);
        String content_code = encoder.encodeToString(content.getBytes());
        String ret = image_code + "|" + content_code;
        System.out.println(content_code);
        System.out.println(ret);
        System.out.println(new String(decoder.decode(content_code)));

        SendMommentObj obj = new SendMommentObj();
        obj.setImage_content_base64(ret);
        MommentService mommentService = new MommentService();
        //mommentService.postMoment(obj,1234567l);
    }
}
