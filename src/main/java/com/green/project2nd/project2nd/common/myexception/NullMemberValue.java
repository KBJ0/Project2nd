package com.green.project2nd.project2nd.common.myexception;

public class NullMemberValue extends RuntimeException {
    public NullMemberValue() {super();}
    public NullMemberValue(String message) {super(message);}
    public NullMemberValue(String message, Throwable cause) {super(message, cause);}
    public NullMemberValue(Throwable cause) {super(cause);}
}
