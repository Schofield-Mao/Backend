package com.server.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.server.server.Exception.NotFoundException;
import com.server.server.dto.QueryObj.PageQueryObj;
import com.server.server.dto.QueryObj.SendMommentObj;
import com.server.server.dto.ResponseObj.MomentDTO;
import com.server.server.Util.CurrentUserId;
import com.server.server.model.ApiResponse;
import com.server.server.model.Moment;
import com.server.server.service.MommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MomentController {
    @Autowired
    MommentService mommentService;

    @ApiOperation("send moment")
    @PostMapping("/moment")
    ApiResponse sendMoment(@RequestBody SendMommentObj obj, @CurrentUserId @ApiParam(hidden = true)Long id){
        mommentService.send_moment(obj,id);
        return ApiResponse.success();
    }

    @ApiOperation("get all moment by user id")
    @GetMapping("/moment")
    ApiResponse getMoment(PageQueryObj pageQuery, @CurrentUserId @ApiParam(hidden = true)Long id){
        //System.out.println("page: "+pageQuery.getPages()+" size: "+pageQuery.getSize());
        IPage<Moment> momentIPage = mommentService.getMomentByUserId(pageQuery,id);
        ApiResponse<IPage> res = new ApiResponse<>();
        res.setData(momentIPage);
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
