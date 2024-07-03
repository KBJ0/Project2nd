package com.green.project2nd.project2nd.plan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostPlanReq {
    @JsonIgnore private long plan_seq;
    private long planPartySeq;
    private String planStartDt;
    private String planStartTime;
    private String planTitle;
    private String planContents;
}
