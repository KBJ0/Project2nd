package com.green.project2nd.user.userexception;


import static com.green.project2nd.user.userexception.ConstMessage.LOGIN_MESSAGE;

public class LoginException extends RuntimeException {
    public LoginException(String msg) {
        super(msg);
    }
}
