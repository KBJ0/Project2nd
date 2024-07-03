package com.green.project2nd.project2nd.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SignUpReq {

    private String userEmail;
    private String userPw;
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
