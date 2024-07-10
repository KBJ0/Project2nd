package com.green.project2nd.user.userexception;

import static com.green.project2nd.user.userexception.ConstUser.PASSWORD_CHECK_MESSAGE;

public class PwCheckException extends RuntimeException{
    public PwCheckException(String msg) {
        super(PASSWORD_CHECK_MESSAGE);
    }
}
