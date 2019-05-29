package com.server.server.model;

public enum Scopes {
    REFRE_TOKEN;
    public String authority(){
        return "ROLE_"+this.name();
    }
}
