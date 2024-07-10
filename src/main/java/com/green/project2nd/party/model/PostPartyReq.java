package com.green.project2nd.party.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class PostPartyReq {
    private long userSeq;
    @JsonIgnore
    private long partySeq;
    private String partyName;

    private int partyGenre;
    private int partyLocation;
    private int partyMinAge;
    private int partyMaxAge;
    private int partyGender;
    private int partyMaximum;
    private int partyJoinGb;

    private String partyIntro;
    private String partyJoinForm;
    @JsonIgnore
    private String partyPic;

}
