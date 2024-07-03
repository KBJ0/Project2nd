package com.green.project2nd.project2nd.member.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PostMemberReq {
    @JsonIgnore
    private Long memberSeq;
    @JsonIgnore
    private Long memberPartySeq;
    private Long memberUserSeq;
}
