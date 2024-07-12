package com.green.project2nd.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Date;

@Getter
public class FindUserReq {
    @Schema(example = "가나다", description = "유저 이름")
    private String userName;
    @Schema(example = "01012345678", description = "유저 전화번호")
    private String userPhone;
    @Schema(example = "2024-01-01", description = "유저 생년월일")
    private String userBirth;
}
