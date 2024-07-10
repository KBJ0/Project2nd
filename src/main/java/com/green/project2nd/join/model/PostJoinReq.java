package com.green.project2nd.join.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PostJoinReq {
    @JsonIgnore
    private Long joinSeq;
    @JsonIgnore
    private Long joinPartySeq;
    private Long joinUserSeq;
    private String joinMsg;
}
