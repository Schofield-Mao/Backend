package com.server.server.Util;

import com.server.server.model.meta.BaseEnum;

public enum  Table implements BaseEnum {

    USER("USER",63),
    REPLY("REPLY",2),
    MOMENT("MOMENT",3),
    MULTIPART("MOMENT",3);

    String description;
    int val;

    @Override
    public Integer getVal() {
        return val;
    }

    @Override
    public String getDescription() {
        return description;
    }

    Table(String desc, int val){
        this.description = desc;
        this.val = val;
    }
}
