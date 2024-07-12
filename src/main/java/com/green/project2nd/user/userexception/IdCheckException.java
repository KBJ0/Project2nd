package com.green.project2nd.user.userexception;


import static com.green.project2nd.user.userexception.ConstMessage.ID_CHECK_MESSAGE;

public class IdCheckException extends RuntimeException {
    public IdCheckException(String msg) {
        super(msg);
    }
}
