package com.green.project2nd.user.userexception;

import static com.green.project2nd.user.userexception.ConstUser.NOT_FOUND_MESSAGE;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(NOT_FOUND_MESSAGE);
    }
}
