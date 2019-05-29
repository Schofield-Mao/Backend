package com.server.server.model.meta;

import com.server.server.model.meta.BaseEnum;

public enum Role implements BaseEnum{
    ADMIN(0,"admin"),
    PREMIUM_MEMBER(1,"premium_member"),
    MEMBER(2,"member");
    public String authority() {
        return "ROLE_" + this.name();
    }

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
