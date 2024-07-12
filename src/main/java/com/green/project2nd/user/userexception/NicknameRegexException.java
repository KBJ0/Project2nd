package com.green.project2nd.user.userexception;


import static com.green.project2nd.user.userexception.ConstMessage.NICKNAME_REGEX_MESSAGE;

public class NicknameRegexException extends RuntimeException {
    public NicknameRegexException(String msg) {
        super(msg);
    }
}
