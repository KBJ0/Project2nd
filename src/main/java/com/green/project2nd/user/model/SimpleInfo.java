package com.green.project2nd.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SimpleInfo {
    @Schema(example = "1", description = "유저 PK 값")
    private Long userSeq;
    @Schema(example = "abc123@naver.com", description = "유저 이메일")
    private String userEmail;
    @Schema(example = "abcd1234!", description = "유저 비밀번호")
    private String userPw;
    @Schema(example = "닉네임123", description = "유저 닉네임")
    private String userNickname;
    @Schema(example = "asdfqwer123.jpg", description = "유저 프로필 사진")
    private String userPic;
}
