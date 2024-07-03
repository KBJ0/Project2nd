package com.green.project2nd.plan.model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetPlanRes {
    private long plan_seq;
    private String planStartDt;
    private String planStartTime;
    private String planTitle;
    private String planContents;
}
