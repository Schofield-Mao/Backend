package com.server.server.Exception;

public class DuplicateUsernameException extends Exception {
    public DuplicateUsernameException(String msg){
        super(msg);
    }
}
