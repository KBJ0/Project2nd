package com.green.project2nd.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserInfoReq {
    @Schema(example = "닉네임123", description = "유저 닉네임")
    @NotBlank(message = "닉네임은 필수값")
    @Pattern(regexp = "^[0-9a-zA-Z가-힣]{4,10}$", message = "닉네임 조건을 확인해 주세요.")
    private String userNickname;

    @Schema(example = "대구 중구 중앙로", description = "유저 주소")
    @NotBlank(message = "주소는 필수값")
    private String userAddr;

    @Schema(example = "운동, 독서, 게임", description = "유저 관심모임")
    private String userFav;

    @Schema(example = "01012345678", description = "유저 전화번호")
    @NotBlank(message = "전화번호는 필수값")
    @Pattern(regexp = "^01[01](?:\\d{3}|\\d{4})\\d{4}$", message = "비밀번호 조건을 확인해 주세요.")
    private String userPhone;

    @Schema(example = "안녕하세요 가지가지 나뭇가지", description = "유저 자기소개")
    private String userIntro;

    private long userSeq;
}
