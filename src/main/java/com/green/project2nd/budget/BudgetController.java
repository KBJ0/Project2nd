package com.green.project2nd.budget;

import com.green.project2nd.budget.model.*;
import com.green.project2nd.common.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/budget")
@Tag(name = "Budget (회계 관리)", description = "Budget CRUD")
public class BudgetController {
    private final BudgetService service;

    @PostMapping
    @Operation(summary = "회계 내역 등록" , description = "회비 입출금 내역 등록 (모임장 or 회계 담당 멤버)")
    public ResultDto<Integer> postBudget(@RequestBody PostBudgetReq p) {
        int result = service.postBudget(p);

        return ResultDto.<Integer>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(result).
                build();
    }

    @PatchMapping()
    @Operation(summary = "회계 내역 수정" , description = "회비 입출금 내역 수정 (모임장 or 회계 담당 멤버)")
    public ResultDto<Integer> patchBudget(@RequestBody PatchBudgetReq p) {
        int result = service.patchBudget(p);

        return ResultDto.<Integer>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(result).
                build();
    }

    @GetMapping
    @Operation(summary = "월 별 회계 내역 조회" , description = "월 별 회비 입출금 내역 조회")
    public ResultDto<List<GetBudgetRes>> getBudget(@RequestBody GetBudgetReq p) {
        List<GetBudgetRes> result = service.getBudget(p);

        return ResultDto.<List<GetBudgetRes>>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(result).
                build();
    }

    @GetMapping("{budget_seq}")
    @Operation(summary = "회계 사진 조회" , description = "회비 출금 내역 기록용 사진 조회")
    public ResultDto<String> getBudgetPic(@PathVariable(name = "budget_seq") long budgetSeq) {
        String result = service.getBudgetPic(budgetSeq);

        return ResultDto.<String>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(result).
                build();
    }

    @DeleteMapping("{budget_seq}")
    @Operation(summary = "회계 내역 삭제" , description = "회비 입출금 내역 삭제 (모임장 or 회계 담당 멤버)")
    public ResultDto<Integer> deleteBudget(@PathVariable(name = "budget_seq") long budgetSeq) {
        int result = service.deleteBudget(budgetSeq);

        return ResultDto.<Integer>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(result).
                build();
    }

    @GetMapping("/member")
    @Operation(summary = "멤버 별 회비 입금 내역 조회" , description = "모임 멤버들의 회비 입금 통계")
    public ResultDto<GetBudgetMemberRes> getBudgetMember(@RequestBody GetBudgetReq p) {
        GetBudgetMemberRes result = service.getBudgetMember(p);
        log.info("p:{}", p);

        return ResultDto.<GetBudgetMemberRes>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(result).
                build();
    }

    @GetMapping("/month")
    @Operation(summary = "월 별 정산 내역 출력" , description = "월 별 입금, 출금 합계 및 통계 출력")
    public ResultDto<GetBudgetMonthlyRes> getBudgetMonthly(@RequestBody GetBudgetReq p) {
        GetBudgetMonthlyRes result = service.getBudgetMonthly(p);

        return ResultDto.<GetBudgetMonthlyRes>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(result).
                build();
    }

}
