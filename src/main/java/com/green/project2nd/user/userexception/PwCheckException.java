package com.green.project2nd.user.userexception;


import static com.green.project2nd.user.userexception.ConstMessage.PASSWORD_CHECK_MESSAGE;

public class PwCheckException extends RuntimeException{
    public PwCheckException(String msg) {
        super(msg);
    }
}