package com.green.project2nd.budget;

import com.green.project2nd.budget.model.*;
import com.green.project2nd.member.model.GetMemberRes;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BudgetMapper {
    int postBudget(PostBudgetReq p);
    int patchBudget(PatchBudgetReq p);
    List<GetBudgetRes> getBudget(long budgetPartySeq, String month);
    GetBudgetPicRes getBudgetPic(long budgetSeq);
    long deleteBudget(long budgetSeq);
    GetBudgetMemberRes getBudgetMember(long budgetPartySeq, String month);
    GetBudgetMonthlyRes getBudgetMonthly(long budgetPartySeq, String month);
    List<GetMemberListRes> getMemberList(long memberPartySeq);
}
