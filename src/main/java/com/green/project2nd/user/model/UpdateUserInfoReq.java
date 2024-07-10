package com.green.project2nd.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserInfoReq {
    @Schema(example = "닉네임123", description = "유저 닉네임")
    private String userNickname;
    @Schema(example = "대구 중구 중앙로", description = "유저 주소")
    private String userAddr;
    @Schema(example = "운동, 독서, 게임", description = "유저 관심모임")
    private String userFav;
    @Schema(example = "010-1234-5678 (프론트랑 상의 필요)", description = "유저 전화번호")
    private String userPhone;
    @Schema(example = "안녕하세요 가지가지 나뭇가지", description = "유저 자기소개")
    private String userIntro;

    private long userSeq;
}
