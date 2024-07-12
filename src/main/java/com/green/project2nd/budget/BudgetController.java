package com.green.project2nd.budget;

import com.green.project2nd.budget.model.*;
import com.green.project2nd.common.model.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.green.project2nd.budget.exception.ConstMessage.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/budget")
@Tag(name = "Budget (회계 관리)", description = "Budget CRUD")
public class BudgetController {
    private final BudgetService service;

    @PostMapping
    @Operation(summary = "회계 내역 등록" , description = "회비 입출금 내역 등록 (모임장 or 회계 담당 멤버)")
    public ResultDto<Integer> postBudget(@RequestPart(required = false) MultipartFile budgetPic, @RequestPart PostBudgetReq p) {
        if (p == null) throw new NullPointerException(NULL_ERROR_MESSAGE);
        service.postBudget(budgetPic, p);
        return ResultDto.resultDto(1, POST_SUCCESS_MESSAGE);
    }

    @PatchMapping()
    @Operation(summary = "회계 내역 수정" , description = "회비 입출금 내역 수정 (모임장 or 회계 담당 멤버)\n 수정할 부분만 입력해도 가능")
    public ResultDto<Integer> patchBudget(@RequestPart(required = false) MultipartFile budgetPic, @RequestPart PatchBudgetReq p) {
        if (p == null) throw new NullPointerException(NULL_ERROR_MESSAGE);
        service.patchBudget(budgetPic, p);
        return ResultDto.resultDto(1, PATCH_SUCCESS_MESSAGE);
    }

    @GetMapping
    @Operation(summary = "월 별 회계 내역 조회" , description = "월 별 회비 입출금 내역 조회\n month 값은 '07', '12'와 같이 입력")
    public ResultDto<List<GetBudgetRes>> getBudget(@RequestParam long budgetPartySeq, @RequestParam String month) {
        return ResultDto.resultDto(1, GET_SUCCESS_MESSAGE, service.getBudget(budgetPartySeq, month));
    }

    @GetMapping("{budget_seq}")
    @Operation(summary = "회계 사진 조회" , description = "회비 출금 내역 기록용 사진 조회")
    public ResultDto<GetBudgetPicRes> getBudgetPic(@PathVariable(name = "budget_seq") long budgetSeq) {
        return ResultDto.resultDto(1, GET_SUCCESS_MESSAGE, service.getBudgetPic(budgetSeq));
    }

    @DeleteMapping
    @Operation(summary = "회계 내역 삭제" , description = "회비 입출금 내역 삭제 (모임장 or 회계 담당 멤버)")
    public ResultDto<Long> deleteBudget(@RequestParam(name = "budget_seq") long budgetSeq) {
        service.deleteBudget(budgetSeq);
        return ResultDto.resultDto(1, DELETE_SUCCESS_MESSAGE);
    }

    @GetMapping("/member")
    @Operation(summary = "멤버 별 회비 입금 내역 조회" , description = "모임 멤버들의 회비 입금 통계\n month 값은 '07', '12'와 같이 입력")
    public ResultDto<GetBudgetMemberRes> getBudgetMember(@RequestParam long budgetPartySeq, @RequestParam String month) {
        return ResultDto.resultDto(1, GET_SUCCESS_MESSAGE, service.getBudgetMember(budgetPartySeq, month));
    }

    @GetMapping("/month")
    @Operation(summary = "월 별 정산 내역 출력" , description = "월 별 입금, 출금 합계 및 통계 출력\n month 값은 '07', '12'와 같이 입력")
    public ResultDto<GetBudgetMonthlyRes> getBudgetMonthly(@RequestParam long budgetPartySeq, @RequestParam String month) {
        return ResultDto.resultDto(1, GET_SUCCESS_MESSAGE, service.getBudgetMonthly(budgetPartySeq, month));
    }

}
