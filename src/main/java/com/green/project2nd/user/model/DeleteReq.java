package com.green.project2nd.user.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DeleteReq {
    @Schema(example = "1", description = "유저 PK 값")
    private Long userSeq;
}
