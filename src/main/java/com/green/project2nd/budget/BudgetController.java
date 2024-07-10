package com.green.project2nd.budget;

import com.green.project2nd.budget.model.*;
import com.green.project2nd.common.model.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        int result = service.postBudget(budgetPic, p);

        return ResultDto.<Integer>builder().
                statusCode(HttpStatus.OK).
                resultMsg(POST_SUCCESS_MESSAGE).
                resultData(result).
                build();
    }

    @PatchMapping()
    @Operation(summary = "회계 내역 수정" , description = "회비 입출금 내역 수정 (모임장 or 회계 담당 멤버)")
    public ResultDto<Integer> patchBudget(@RequestPart(required = false) MultipartFile budgetPic, @RequestPart PatchBudgetReq p) {
        if (p == null) throw new NullPointerException(NULL_ERROR_MESSAGE);
        int result = service.patchBudget(budgetPic,p);

        return ResultDto.<Integer>builder().
                statusCode(HttpStatus.OK).
                resultMsg(PATCH_SUCCESS_MESSAGE).
                resultData(result).
                build();
    }

    @GetMapping
    @Operation(summary = "월 별 회계 내역 조회" , description = "월 별 회비 입출금 내역 조회\n month 값은 '07', '12'와 같이 입력")
    public ResultDto<List<GetBudgetRes>> getBudget(@RequestParam long budgetPartySeq, @RequestParam String month) {
        List<GetBudgetRes> result = service.getBudget(budgetPartySeq, month);

        return ResultDto.<List<GetBudgetRes>>builder().
                statusCode(HttpStatus.OK).
                resultMsg(GET_SUCCESS_MESSAGE).
                resultData(result).
                build();
    }

    @GetMapping("{budget_seq}")
    @Operation(summary = "회계 사진 조회" , description = "회비 출금 내역 기록용 사진 조회")
    public ResultDto<GetBudgetPicRes> getBudgetPic(@PathVariable(name = "budget_seq") long budgetSeq) {
        GetBudgetPicRes result = service.getBudgetPic(budgetSeq);

        return ResultDto.<GetBudgetPicRes>builder().
                statusCode(HttpStatus.OK).
                resultMsg(GET_SUCCESS_MESSAGE).
                resultData(result).
                build();
    }

    @DeleteMapping
    @Operation(summary = "회계 내역 삭제" , description = "회비 입출금 내역 삭제 (모임장 or 회계 담당 멤버)")
    public ResultDto<Long> deleteBudget(@RequestParam(name = "budget_seq") long budgetSeq) {
        long result = service.deleteBudget(budgetSeq);

        return ResultDto.<Long>builder().
                statusCode(HttpStatus.OK).
                resultMsg(DELETE_SUCCESS_MESSAGE).
                resultData(result).
                build();
    }

    @GetMapping("/member")
    @Operation(summary = "멤버 별 회비 입금 내역 조회" , description = "모임 멤버들의 회비 입금 통계\n month 값은 '07', '12'와 같이 입력")
    public ResultDto<GetBudgetMemberRes> getBudgetMember(@RequestParam long budgetPartySeq, @RequestParam String month) {
        GetBudgetMemberRes result = service.getBudgetMember(budgetPartySeq, month);

        return ResultDto.<GetBudgetMemberRes>builder().
                statusCode(HttpStatus.OK).
                resultMsg(GET_SUCCESS_MESSAGE).
                resultData(result).
                build();
    }

    @GetMapping("/month")
    @Operation(summary = "월 별 정산 내역 출력" , description = "월 별 입금, 출금 합계 및 통계 출력\n month 값은 '07', '12'와 같이 입력")
    public ResultDto<GetBudgetMonthlyRes> getBudgetMonthly(@RequestParam long budgetPartySeq, @RequestParam String month) {
        GetBudgetMonthlyRes result = service.getBudgetMonthly(budgetPartySeq, month);

        return ResultDto.<GetBudgetMonthlyRes>builder().
                statusCode(HttpStatus.OK).
                resultMsg(GET_SUCCESS_MESSAGE).
                resultData(result).
                build();
    }

}
