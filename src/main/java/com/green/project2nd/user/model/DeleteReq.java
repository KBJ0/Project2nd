package com.green.project2nd.user.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeleteReq {
    @Schema(example = "1", description = "유저 PK 값")
    @JsonIgnore
    private long userSeq;

    private String userPw;

}
