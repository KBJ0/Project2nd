package com.green.project2nd.user.userexception;

import static com.green.project2nd.user.userexception.ConstUser.LOGIN_MESSAGE;

public class LoginException extends RuntimeException {
    public LoginException(String msg) {
        super(LOGIN_MESSAGE);
    }
}
