package com.server.server.Exception;

import com.fasterxml.jackson.databind.util.ObjectBuffer;
import com.server.server.model.ApiResponse;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class ApiException extends Exception {
    private String message;

    public ApiException(String message){
        this.message = message;
    }
}
