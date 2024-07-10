package com.green.project2nd.party.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UpdatePartyReq {
    private long userSeq;
    private long partySeq;
    private String partyName;
    private String partyGenre;
    private String partyLocation;
    private int partyMinAge;
    private int partyMaxAge;
    private int partyGender;
    private int partyMaximum;
    private String partyIntro;
    private String partyJoinForm;
    @JsonIgnore
    private String partyPic;
}
