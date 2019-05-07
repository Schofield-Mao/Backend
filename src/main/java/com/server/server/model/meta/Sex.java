package com.server.server.model.meta;

import com.server.server.model.meta.BaseEnum;

public enum Sex implements BaseEnum {
    MAN(0,"man"),
    WOMAN(1,"woman"),
    UNKNOWN(2,"unknown");

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

    Sex(Integer val, String desctiption){
        this.val = val;
        this.desctiption = desctiption;
    }

}
