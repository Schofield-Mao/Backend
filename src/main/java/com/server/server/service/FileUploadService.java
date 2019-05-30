package com.server.server.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@Configuration
@ConfigurationProperties("filepath")
@Data
public class FileUploadService {

    private String imagePath;

    public String generalFileUpload(MultipartFile file, String dirPath){
        try{
            String fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            String localFileName = System.currentTimeMillis() + fileSuffix;
            String filePath = dirPath + File.separator + localFileName;
            File localFile = new File(filePath);
            File imagePath = new File(dirPath);
            if (!imagePath.exists()) {
                imagePath.mkdirs();
            }
            file.transferTo(localFile);
            return localFileName;
        }catch (IOException e){
            return e.getMessage();
        }
    }

    public String imageFileUpload(MultipartFile file){
        return generalFileUpload(file, imagePath);
    }

    public String imageByteUpload(String image_base64){
        String imageName = "";
        String path = "";
        try{
            imageName = image_base64.substring(0,10)+System.currentTimeMillis() + ".jpg";
            path = imagePath+File.separator+imageName;
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }

            byte[] im = Base64.getDecoder().decode(image_base64);
            FileOutputStream fs = new FileOutputStream(file);
            BufferedOutputStream bs = new BufferedOutputStream(fs);
            bs.write(im,0, im.length);
        }catch (IOException e){
            e.printStackTrace();
        }
        return path;
    }


}
