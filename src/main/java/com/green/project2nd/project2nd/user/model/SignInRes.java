package com.green.project2nd.project2nd.user.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInRes {
    private Long userSeq;
    private String userNickname;
    private String userPic;
}
