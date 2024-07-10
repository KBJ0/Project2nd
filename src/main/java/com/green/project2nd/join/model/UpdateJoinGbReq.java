package com.green.project2nd.join.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UpdateJoinGbReq {
    @JsonIgnore
    private Long joinPartySeq;
    private Long joinUserSeq;
    private Long leaderUserSeq;
    private int joinGb;
}
