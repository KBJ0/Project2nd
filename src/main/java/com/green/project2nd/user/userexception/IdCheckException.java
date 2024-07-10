package com.green.project2nd.user.userexception;

import static com.green.project2nd.user.userexception.ConstUser.ID_CHECK_MESSAGE;

public class IdCheckException extends RuntimeException {
    public IdCheckException(String msg) {
        super(ID_CHECK_MESSAGE);
    }
}
