package com.green.project2nd.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordReq {
    @Schema(example = "abc123@naver.com", description = "유저 이메일")
    private String userEmail;
    @Schema(example = "abcd1234!", description = "유저 비밀번호")
    private String userPw;
    @Schema(example = "1234abcd!", description = "유저 새 비밀번호")
    private String userNewPw;
    @Schema(example = "1234abcd!", description = "유저 새 비밀번호 확인")
    private String userPwCheck;


    @JsonIgnore
    private long userSeq;
}
