package com.green.project2nd.budget;

import com.green.project2nd.budget.exception.BudgetExceptionHandler;
import com.green.project2nd.budget.model.*;
import com.green.project2nd.common.model.CustomFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.green.project2nd.budget.exception.ConstMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final BudgetExceptionHandler check;

    @Transactional
    public int postBudget(MultipartFile budgetPic, PostBudgetReq p) {
        check.exception(budgetPic);

        String saveFileName = customFileUtils.makeRandomFileName(budgetPic);
        p.setBudgetPic(saveFileName);
        String path = String.format("budget/%d", p.getBudgetSeq());
        customFileUtils.makeFolders(path);

        String target = String.format("%s/%s", path, saveFileName);
        try {
            customFileUtils.transferTo(budgetPic, target);
        } catch (Exception e) {
            throw new RuntimeException(PIC_SAVE_ERROR);
        }

        return mapper.postBudget(p);
    }

    @Transactional
    public int patchBudget(MultipartFile budgetPic, PatchBudgetReq p) {
        check.exception(budgetPic);

        String path = String.format("budget/%d", p.getBudgetSeq());
        String saveFileName = customFileUtils.makeRandomFileName();
        String target = String.format("%s/%s", path, saveFileName);

        try {
            customFileUtils.deleteFolder(path);
            customFileUtils.makeFolders(path);
            customFileUtils.transferTo(budgetPic, target);
        } catch (Exception e) {
            throw new RuntimeException(PIC_SAVE_ERROR);
        }
        p.setBudgetPic(saveFileName);
        return mapper.patchBudget(p);
    }

    public List<GetBudgetRes> getBudget(long budgetPartySeq, String month) {
        check.exception(budgetPartySeq);
        return mapper.getBudget(budgetPartySeq, month);
    }

    public GetBudgetPicRes getBudgetPic(long budgetSeq) {
        check.exception2(budgetSeq);
        GetBudgetPicRes result = mapper.getBudgetPic(budgetSeq);
        if(result == null) {
            throw new NullPointerException(NULL_ERROR_MESSAGE);
        }
        return result;
    }

    public long deleteBudget(long budgetSeq) {
        check.exception2(budgetSeq);
        return mapper.deleteBudget(budgetSeq);
    }

    public GetBudgetMemberRes getBudgetMember(long budgetPartySeq, String month) {
        check.exception(budgetPartySeq);
        return mapper.getBudgetMember(budgetPartySeq, month);
    }

    public GetBudgetMonthlyRes getBudgetMonthly(long budgetPartySeq, String month) {
        check.exception(budgetPartySeq);
        return mapper.getBudgetMonthly(budgetPartySeq, month);
    }

}
