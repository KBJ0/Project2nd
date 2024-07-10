package com.green.project2nd.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRes {
    @Schema(example = "1", description = "유저 PK 값")
    private Long userSeq;
    @Schema(example = "닉네임123", description = "유저 닉네임")
    private String userNickname;
    @Schema(example = "asdfqwer123.jpg", description = "유저 프로필 사진")
    private String userPic;


    private String accessToken;
}
