package com.green.project2nd.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SimpleInfo {

    private Long userSeq;

    private String userEmail;

    private String userPw;

    private String userNickname;

    private String userPic;
}