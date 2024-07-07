package com.green.project2nd.budget;

import com.green.project2nd.budget.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetMapper mapper;

    public int postBudget(PostBudgetReq p) {
        return mapper.postBudget(p);
    }

    public int patchBudget(PatchBudgetReq p) {
        return mapper.patchBudget(p);
    }

    public List<GetBudgetRes> getBudget(GetBudgetReq p) {
        return mapper.getBudget(p);
    }

    public String getBudgetPic(long budgetSeq) {
        return mapper.getBudgetPic(budgetSeq);
    }


    public int deleteBudget(long budgetSeq) {
        return mapper.deleteBudget(budgetSeq);
    }

    public GetBudgetMemberRes getBudgetMember(GetBudgetReq p) {
        return mapper.getBudgetMember(p);
    }

    public GetBudgetMonthlyRes getBudgetMonthly(GetBudgetReq p) {
        return mapper.getBudgetMonthly(p);
    }

}
