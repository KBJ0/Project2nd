package com.green.project2nd.join.model;

import lombok.Data;

@Data
public class GetJoinRes {
    private Long joinSeq;
    private Long joinUserSeq;
    private String joinMsg;
    private int joinGb;
}
