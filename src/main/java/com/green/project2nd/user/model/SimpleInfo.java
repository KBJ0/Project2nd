package com.green.project2nd.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SimpleInfo {

    private long userSeq;

    private String userEmail;

    private String userPw;

    private String userNickname;

    private String userPic;

    private String userName;
    private int userGender;
    private String userBirth;
    private String userGenderNm;

    private String userAddr;
    private String userPhone;
}
