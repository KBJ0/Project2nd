package com.green.project2nd.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "이메일은 필수값")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,15}@[a-z]{3,7}\\.(com|net){1}$", message = "이메일 조건을 확인해 주세요")
    private String userEmail;

    @Schema(example = "abcd1234!", description = "유저 비밀번호")
    @NotBlank(message = "비밀번호는 필수값")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])\\S{10,20}$", message = "비밀번호 조건을 확인해 주세요")
    private String userPw;

    @Schema(example = "abcd1234!", description = "유저 비밀번호 확인")
    @NotBlank(message = "비밀번호를 확인해 주세요")
    private String userPwCheck;

    @Schema(example = "가나다", description = "유저 이름")
    @NotBlank(message = "이름은 필수값")
    @Pattern(regexp = "^[가-힣]{2,6}$")
    private String userName;

    @Schema(example = "대구 중구 중앙로", description = "유저 주소")
    @NotBlank(message = "주소는 필수값")
    private String userAddr;

    @Schema(example = "닉네임123", description = "유저 닉네임")
    @NotBlank(message = "닉네임은 필수값")
    @Pattern(regexp = "^[0-9a-zA-Z가-힣]{4,10}$", message = "닉네임 조건을 확인해 주세요")
    private String userNickname;

    @Schema(example = "운동, 독서, 게임", description = "유저 관심모임")
    private String userFav;

    @Schema(example = "2024-01-01", description = "유저 생년월일")
    @NotBlank(message = "생년월일은 필수값")
    private String userBirth;

    @Schema(example = "남자(2) / 여자(1)", description = "유저 성별")
    @NotNull(message = "성별은 필수값")
    private int userGender;

    @Schema(example = "01012345678", description = "유저 전화번호")
    @NotBlank(message = "전화번호는 필수값")
    @Pattern(regexp = "^01[01](?:\\d{3}|\\d{4})\\d{4}$")
    private String userPhone;

    @Schema(example = "안녕하세요 가지가지 나뭇가지", description = "유저 자기소개")
    private String userIntro;

    @JsonIgnore
    private String userPic;

    @JsonIgnore
    private long userSeq;
}
