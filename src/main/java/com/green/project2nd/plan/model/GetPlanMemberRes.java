package com.green.project2nd.plan.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetPlanMemberRes {
    private long planSeq;
    private String userName;
}