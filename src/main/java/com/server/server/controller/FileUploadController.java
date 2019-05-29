package com.server.server.controller;

import com.server.server.model.ApiResponse;
import com.server.server.service.FileUploadService;
import com.server.server.service.MultipartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    @ApiOperation("image upload")
    @PostMapping("/image")
    public ApiResponse handleFormUpload(@RequestParam("file") MultipartFile file) {
        ApiResponse res = new ApiResponse();
        try{
            if (!file.isEmpty()) {
                String filePath =  fileUploadService.imageFileUpload(file);
                res.setMessage(filePath);
                return res;
            }
        }catch (Exception e){

            res.setMessage(e.getMessage());
            return res;
        }

        return ApiResponse.fail();
    }

}
