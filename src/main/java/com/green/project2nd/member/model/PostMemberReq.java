package com.green.project2nd.member.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PostMemberReq {
    @JsonIgnore
    private Long memberSeq;
    @JsonIgnore
    private Long memberPartySeq;
    @Schema(example = "1", description = "유저 PK")
    private Long memberUserSeq;
}
