package com.server.server.dto.QueryObj;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SendMommentObj {
    @ApiParam
    private String image_content_base64;
}
