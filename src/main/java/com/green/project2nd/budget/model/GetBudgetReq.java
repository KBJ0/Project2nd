package com.green.project2nd.budget.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetBudgetReq {
    private long budgetPartySeq;
    private String monthReq;
    @JsonIgnore
    private String month = "'2024-" + monthReq + "%'";
}