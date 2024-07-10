package com.green.project2nd.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class SignUpReq {

    private String userEmail;
    private String userPw;
    private String userPwCheck;
    private String userName;
    private String userAddr;
    private String userNickname;
    private String userFav;
    private Date userBirth;
    private int userGender;
    private String userPhone;
    private String userIntro;

    @JsonIgnore
    private String userPic;

    @JsonIgnore
    private long userSeq;
}
