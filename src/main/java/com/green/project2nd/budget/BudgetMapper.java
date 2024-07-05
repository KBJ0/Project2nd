package com.green.project2nd.budget;

import com.green.project2nd.budget.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BudgetMapper {
    int postBudget(PostBudgetReq p);
    int patchBudget(PatchBudgetReq p);
    List<GetBudgetRes> getBudget(GetBudgetReq p);
    int deleteBudget(long budgetSeq);
    String getBudgetPic(long budgetSeq);
    GetBudgetMemberRes getBudgetMember(long budgetSeq);
    GetBudgetMonthlyRes getBudgetMonthly(GetBudgetReq p);
}
