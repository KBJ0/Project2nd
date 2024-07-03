package com.green.project2nd.project2nd.member.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UpdateMemberReq {
    @JsonIgnore
    private Long memberPartySeq;
    private Long memberUserSeq;
    private String memberRole;

}
