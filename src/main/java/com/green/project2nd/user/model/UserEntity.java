package com.green.project2nd.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Date;

@Getter
public class UserEntity {
    @Schema(example = "1", description = "유저 PK 값")
    private long userSeq;
    @Schema(example = "abc123@naver.com", description = "유저 이메일")
    private String userEmail;
    @Schema(example = "가나다", description = "유저 이름")
    private String userName;
    @Schema(example = "닉네임123", description = "유저 닉네임")
    private String userNickname;
    @Schema(example = "대구 중구 중앙로", description = "유저 주소")
    private String userAddr;
    @Schema(example = "운동, 독서, 게임", description = "유저 관심모임")
    private String userFav;
    @Schema(example = "2024-01-01 (프론트랑 상의 필요)", description = "유저 생년월일")
    private Date userBirth;
    @Schema(example = "남자 / 여자", description = "유저 성별")
    private int userGender;
    private String userGenderNm;
    @Schema(example = "010-1234-5678 (프론트랑 상의 필요)", description = "유저 전화번호")
    private String userPhone;
    @Schema(example = "안녕하세요 가지가지 나뭇가지", description = "유저 자기소개")
    private String userIntro;
    @Schema(example = "1 : 미인증 / 2 : 인증", description = "유저 이메일 인증")
    private int userGb;
    private String userEmailAuthStatus;
    @Schema(example = "asdfqwer123.jpg", description = "유저 프로필 사진")
    private String userPic;
}
