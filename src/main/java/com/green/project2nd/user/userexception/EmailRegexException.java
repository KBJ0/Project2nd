package com.green.project2nd.user.userexception;

import static com.green.project2nd.user.userexception.ConstUser.EMAIL_REGEX_MESSAGE;

public class EmailRegexException extends RuntimeException{
    public EmailRegexException(String msg) {
        super(EMAIL_REGEX_MESSAGE);
    }
}
