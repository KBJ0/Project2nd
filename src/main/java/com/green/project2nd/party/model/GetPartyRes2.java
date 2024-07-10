package com.green.project2nd.party.model;


import lombok.Data;

@Data
public class GetPartyRes2 {
    private long partySeq;
    private String userName;
    private String partyName;
    private String partyAuthGb;

    //스웨거 보내기용
    private int partyNowMem;
    private int partyMaximum;

    private String partyPic;
}
