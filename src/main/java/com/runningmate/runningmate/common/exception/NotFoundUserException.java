package com.runningmate.runningmate.common.exception;

public class NotFoundUserException extends RuntimeException{
    public NotFoundUserException(String message){
        super(message );
    }
}