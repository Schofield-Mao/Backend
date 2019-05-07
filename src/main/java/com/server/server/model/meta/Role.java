package com.server.server.model.meta;

import com.server.server.model.meta.BaseEnum;

public enum Role implements BaseEnum {
    ADMIN(0,"admin"),
    USER(1,"user");

    Integer val;
    String desctiption;
    @Override
    public Integer getVal() {
        return this.val;
    }

    @Override
    public String getDescription() {
        return this.desctiption;
    }

    Role(Integer val, String desctiption){
        this.val = val;
        this.desctiption = desctiption;
    }
}
