package com.green.project2nd.user.model;

import lombok.Getter;

import java.util.Date;

@Getter
public class UserEntity {

    private long userSeq;
    private String userEmail;
    private String userPw;
    private String userName;
    private String userNickname;
    private String userAddr;
    private String userFav;
    private Date userBirth;
    private int userGender;
    private String userPhone;
    private String userIntro;
    private int userGb;
    private String userPic;
}
