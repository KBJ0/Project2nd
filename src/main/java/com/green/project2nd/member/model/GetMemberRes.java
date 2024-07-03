package com.green.project2nd.member.model;

import lombok.Data;

@Data
public class GetMemberRes {
    private Long memberSeq;
    private Long memberUserSeq;
    private String memberRole;
    private int memberGb;
}
