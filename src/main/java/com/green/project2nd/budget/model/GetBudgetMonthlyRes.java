package com.green.project2nd.budget.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetBudgetMonthlyRes {
    private int depositSum;
    private int withdrawSum;
    private int sum;
}