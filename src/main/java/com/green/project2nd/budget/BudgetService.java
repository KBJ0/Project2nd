package com.green.project2nd.budget;

import com.green.project2nd.budget.model.*;
import com.green.project2nd.common.model.CustomFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetMapper mapper;
    private final CustomFileUtils customFileUtils;

    @Transactional
    public int postBudget(MultipartFile budgetPic, PostBudgetReq p) {
        String saveFileName = customFileUtils.makeRandomFileName(budgetPic);
        p.setBudgetPic(saveFileName);
        String path = String.format("budget/%d", p.getBudgetSeq());
        customFileUtils.makeFolders(path);

        String target = String.format("%s/%s", path, saveFileName);
        try {
            customFileUtils.transferTo(budgetPic, target);
        } catch (Exception e) {
            log.error("파일 저장 중 오류 발생", e);
            throw new RuntimeException(e);
        }

        return mapper.postBudget(p);
    }

    @Transactional
    public int patchBudget(MultipartFile budgetPic, PatchBudgetReq p) {
        String path = String.format("budget/%d", p.getBudgetSeq());
        String saveFileName = customFileUtils.makeRandomFileName();
        String target = String.format("%s/%s", path, saveFileName);

        try {
            customFileUtils.deleteFolder(path);
            customFileUtils.makeFolders(path);
            customFileUtils.transferTo(budgetPic, target);
        } catch (Exception e) {
            log.error("파일 저장 중 오류 발생", e);
            throw new RuntimeException(e);
        }
        p.setBudgetPic(saveFileName);
        return mapper.patchBudget(p);
    }

    public List<GetBudgetRes> getBudget(GetBudgetReq p) {
        return mapper.getBudget(p);
    }

    public GetBudgetPicRes getBudgetPic(long budgetSeq) {
        return mapper.getBudgetPic(budgetSeq);
    }

    public long deleteBudget(long budgetSeq) {
        return mapper.deleteBudget(budgetSeq);
    }

    public GetBudgetMemberRes getBudgetMember(long budgetPartySeq, String month) {
        return mapper.getBudgetMember(budgetPartySeq, month);
    }

    public GetBudgetMonthlyRes getBudgetMonthly(long budgetPartySeq, String month) {
        return mapper.getBudgetMonthly(budgetPartySeq, month);
    }

}
