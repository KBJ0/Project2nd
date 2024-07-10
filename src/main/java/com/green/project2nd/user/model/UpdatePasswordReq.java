package com.green.project2nd.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordReq {
    private String userEmail;
    private String upw;
    private String newPw;
    private String userPwCheck;


    @JsonIgnore
    private Long userSeq;
}
