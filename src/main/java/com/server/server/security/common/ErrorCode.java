package com.server.server.security.common;

import com.fasterxml.jackson.annotation.JsonValue;
import com.server.server.model.meta.BaseEnum;

public enum  ErrorCode implements BaseEnum {
    GLOBAL(2,"global"),

    AUTHENTICATION(10, "authentication"),

    JWT_TOKEN_EXPIRED(11,"jwt_token_expired");

    @JsonValue
    Integer val;
    String description;

    ErrorCode(Integer val, String description){
        this.val = val;
        this.description = description;
    }

    @Override
    public Integer getVal(){
        return val;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
