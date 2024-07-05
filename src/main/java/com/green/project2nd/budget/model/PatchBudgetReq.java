package com.green.project2nd.budget.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PatchBudgetReq {
    private long budgetSeq;
    private long budgetMemberSeq;
    private int budgetGb;
    private int budgetAmount;
    private String budgetDt;
    private String budgetText;
    private String budgetPic;
}
