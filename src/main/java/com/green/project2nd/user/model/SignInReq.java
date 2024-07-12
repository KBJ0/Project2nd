package com.green.project2nd.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SignInReq {

    @Schema(example = "abc123@naver.com", description = "유저 이메일")
    private String userEmail;
    @Schema(example = "abcd1234!", description = "유저 비밀번호")
    private String userPw;
}
