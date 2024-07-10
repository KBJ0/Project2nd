package com.green.project2nd.party.model;

import lombok.Data;

@Data
public class GetPartyRes {
    private long partySeq;
    private String partyName;
    private String partyGenre;
    private String partyLocation;
    private int partyMinAge;
    private int partyMaxAge;
    private int partyGender;
    //스웨거 보내기용
    private int partyNowMem;
    private int partyMaximum;
    private String partyIntro;
    private String partyJoinForm;
    private String partyAuthGb;
    private String partyPic;
}
