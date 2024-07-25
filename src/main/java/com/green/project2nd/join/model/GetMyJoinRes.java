package com.green.project2nd.join.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetMyJoinRes {
    private long joinSeq;
    private long joinPartySeq;
    private String joinMsg;
    private long joinGb;
    private String inputDt;
    private long partySeq;
    private String partyName;


}
