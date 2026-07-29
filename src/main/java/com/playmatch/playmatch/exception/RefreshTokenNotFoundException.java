package com.playmatch.playmatch.exception;

public class RefreshTokenNotFoundException extends RuntimeException{
    public RefreshTokenNotFoundException(String msg){
        super(msg);
    }
}
