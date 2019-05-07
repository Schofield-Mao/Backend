package com.server.server.model.meta;

public enum ResponseStatus implements BaseEnum{
    OK(200,"success"),
    NOT_FOUND(404,"resource not found"),
    UN_AUTHORIZE(401,"un authorize"),
    FAIL(400,"fail");


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

    ResponseStatus(Integer val, String desctiption){
        this.val = val;
        this.desctiption = desctiption;
    }
}
