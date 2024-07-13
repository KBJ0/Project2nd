package com.green.project2nd.budget.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetBudgetRes {
    private long budgetSeq;
    private long budgetPartySeq;
    private long budgetMemberSeq;
    private int budgetGb;
    private String cdNm;
    private int budgetAmount;
    private String budgetDt;
    private String budgetText;
    private String budgetPic;
}
