package com.green.project2nd.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@ToString
public class SignUpReq {

    @Schema(example = "abc123@naver.com", description = "유저 이메일")
    private String userEmail;
    @Schema(example = "abcd1234!", description = "유저 비밀번호")
    private String userPw;
    @Schema(example = "abcd1234!", description = "유저 비밀번호 확인")
    private String userPwCheck;
    @Schema(example = "가나다", description = "유저 이름")
    private String userName;
    @Schema(example = "대구 중구 중앙로", description = "유저 주소")
    private String userAddr;
    @Schema(example = "닉네임123", description = "유저 닉네임")
    private String userNickname;
    @Schema(example = "운동, 독서, 게임", description = "유저 관심모임")
    private String userFav;
    @Schema(example = "2024-01-01 (프론트랑 상의 필요)", description = "유저 생년월일")
    private String userBirth;
    @Schema(example = "남자 / 여자", description = "유저 성별")
    private int userGender;
    @Schema(example = "010-1234-5678 (프론트랑 상의 필요)", description = "유저 전화번호")
    private String userPhone;
    @Schema(example = "안녕하세요 가지가지 나뭇가지", description = "유저 자기소개")
    private String userIntro;

    @JsonIgnore
    private String userPic;

    @JsonIgnore
    private long userSeq;
}
