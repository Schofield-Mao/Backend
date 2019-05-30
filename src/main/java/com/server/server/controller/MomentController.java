package com.server.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.server.server.Exception.NotFoundException;
import com.server.server.dto.QueryObj.PageQueryObj;
import com.server.server.dto.QueryObj.SendMommentObj;
import com.server.server.dto.ResponseObj.MomentDTO;
import com.server.server.Util.CurrentUserId;
import com.server.server.dto.ResponseObj.RichMomentDTO;
import com.server.server.model.ApiResponse;
import com.server.server.model.Moment;
import com.server.server.service.FileUploadService;
import com.server.server.service.MommentService;
import com.server.server.service.MultipartService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MomentController {
    @Autowired
    MommentService mommentService;


    @ApiOperation("post moment content encode by base 64 format  picture|...|content")
    @PostMapping("/moment/content")
    ApiResponse postMoment(
            @RequestBody SendMommentObj obj, @CurrentUserId @ApiParam(hidden = true)Long id)throws Exception{
        ApiResponse res = new ApiResponse();
        res.setData(mommentService.postMoment(obj,id));
        return res;
    }

    @ApiOperation("get all moment by user id")
    @GetMapping("/moment")
    ApiResponse getMoment(PageQueryObj pageQuery, @CurrentUserId @ApiParam(hidden = true)Long id){
        //System.out.println("page: "+pageQuery.getPages()+" size: "+pageQuery.getSize());
       PageQueryObj<RichMomentDTO> richMomentDTOPageQueryObj = mommentService.getMomentByUserId(pageQuery,id);
        ApiResponse<IPage> res = new ApiResponse<>();
        res.setData(richMomentDTOPageQueryObj);
        return res;
    }

    @ApiOperation("get moment by momentid")
    @GetMapping("/moment/{momentId}")
    ApiResponse getMomentByMomentId(@PathVariable(value = "momentId")long momentId){
        //System.out.println("page: "+pageQuery.getPages()+" size: "+pageQuery.getSize());
        MomentDTO momentDTO;
        ApiResponse<MomentDTO> res = new ApiResponse<>();
        try{
            momentDTO = mommentService.getMomentByMomentId(momentId);
            res.setData(momentDTO);
            return res;
        }catch (NotFoundException e){
            res.setMessage(e.toString());
            return res;
        }

    }

}
