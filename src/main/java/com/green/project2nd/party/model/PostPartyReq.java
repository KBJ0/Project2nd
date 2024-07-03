package com.green.project2nd.party.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.Year;

@Data
public class PostPartyReq {
    @JsonIgnore
    private long partySeq;
    private String partyName;
    private int partyGenre;
    private int partyLocation;
    private Year partyMinAge;
    private Year partyMaxAge;
    private int partyGender;
    private int partyMaximum;
    private String partyIntro;
    private String partyJoinForm;
    @JsonIgnore
    private String partyPic;
}
