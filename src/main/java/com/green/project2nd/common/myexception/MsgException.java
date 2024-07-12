package com.green.project2nd.common.myexception;

import org.springframework.http.HttpStatus;

public class MsgException extends RuntimeException {
    public MsgException() {super();}
    public MsgException(String message) {super(message);}
    public MsgException(String message, Throwable cause) {super(message, cause);}
    public MsgException(Throwable cause) {super(cause);}
}
