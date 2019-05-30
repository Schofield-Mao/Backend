package com.server.server.dto.ResponseObj;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostMomentResponseDTO {
    private long momentId;
    private List<String> urls = new ArrayList<>();
    public void addUrl(String url){
        this.urls.add(url);
    }
}
