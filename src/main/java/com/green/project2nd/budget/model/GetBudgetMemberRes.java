package com.green.project2nd.budget.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetBudgetMemberRes {
    private int memberSum;
    private int depositedMember;
    private int unDepositedMember;
}
