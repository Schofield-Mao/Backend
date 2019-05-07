package com.server.server.model;

import com.server.server.model.meta.ResponseStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T>{
    @ApiModelProperty("status")
    ResponseStatus status = ResponseStatus.OK;
    @ApiModelProperty("message")
    String message;
    @ApiModelProperty("data")
    T data;
    public ApiResponse(T data){
        this.data = data;
    }
}
