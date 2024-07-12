package com.green.project2nd.budget;

import com.green.project2nd.budget.model.*;
import com.green.project2nd.common.model.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "회계 내역 등록" , description =
            "<strong> 회비 입출금 내역 등록 (모임장 or 회계 담당 멤버) </strong> <p></p>\n" +
            "<p><strong> budgetPartySeq</strong> : 모임 마스터 PK 값 (long) </p>\n"+
            "<p><strong> budgetMemberSeq/strong> : 모임 멤버 PK 값 (long) </p>\n"+
            "<p><strong> budgetGb</strong> : 입금 or 출금 여부 (long) </p>\n" +
            "<p><strong> budgetAmount</strong> : 금액 (int) </p>\n" +
            "<p><strong> budgetDt</strong> : 입출금 날짜 (String) </p>\n" +
            "<p><strong> budgetText</strong> : 입출금 상세 내역 (String) (NULL 허용)</p>\n")
    @ApiResponse(description =
            "<p> ResponseCode 응답 코드 </p> " +
                    "<p>  1 : 성공 </p> " +
                    "<p>  2 : 실패, ResultMsg </p> " +
                    "<p>  99 : 알 수 없는 오류 발생 실패</p> ")
    public ResultDto<Integer> postBudget(@RequestPart(required = false) MultipartFile budgetPic, @RequestPart PostBudgetReq p) {
        if (p == null) throw new NullPointerException(NULL_ERROR_MESSAGE);
        service.postBudget(budgetPic, p);
        return ResultDto.resultDto(HttpStatus.OK, 1, POST_SUCCESS_MESSAGE);
    }

    @PatchMapping
    @Operation(summary = "회계 내역 수정" , description =
            "<strong> 회비 입출금 내역 수정 (모임장 or 회계 담당 멤버) 수정할 부분만 입력해도 가능<p></p>\n" +
            "<p><strong> budgetSeq</strong> : 모임 회계 PK 값 (long) </p>\n"+
            "<p><strong> budgetMemberSeq</strong> : 모임 멤버 PK 값 (long) </p>\n"+
            "<p><strong> budgetGb</strong> : 입금 or 출금 여부 (long) </p>\n" +
            "<p><strong> budgetAmount</strong> : 금액 (int) </p>\n" +
            "<p><strong> budgetDt</strong> : 입출금 날짜 (String) </p>\n" +
            "<p><strong> budgetText</strong> : 입출금 상세 내역 (String) (Null 허용)</p>\n")
    @ApiResponse(description =
            "<p> ResponseCode 응답 코드 </p> " +
                    "<p>  1 : 성공 </p> " +
                    "<p>  2 : 실패, ResultMsg </p> " +
                    "<p>  99 : 알 수 없는 오류 발생 실패</p> ")
    public ResultDto<Integer> patchBudget(@RequestPart(required = false) MultipartFile budgetPic, @RequestPart PatchBudgetReq p) {
        if (p == null) throw new NullPointerException(NULL_ERROR_MESSAGE);
        service.patchBudget(budgetPic, p);
        return ResultDto.resultDto(HttpStatus.OK, 1, PATCH_SUCCESS_MESSAGE);
    }

    @GetMapping
    @Operation(summary = "월 별 회계 내역 조회" , description =
            "<strong> 월 별 회비 입출금 내역 조회. month 값은 '07', '12'와 같이 입력<p></p>\n" +
            "<p><strong> budgetPartySeq</strong> : 모임 마스터 PK 값 (long) </p>\n")
    @ApiResponse(description =
            "<p> ResponseCode 응답 코드 </p> " +
                    "<p>  1 : 성공 </p> " +
                    "<p>  2 : 실패, ResultMsg </p> " +
                    "<p>  99 : 알 수 없는 오류 발생 실패</p> ")
    public ResultDto<List<GetBudgetRes>> getBudget(@RequestParam long budgetPartySeq, @RequestParam String month) {
        return ResultDto.resultDto(HttpStatus.OK, 1, GET_SUCCESS_MESSAGE, service.getBudget(budgetPartySeq, month));
    }

    @GetMapping("{budget_seq}")
    @Operation(summary = "회계 사진 조회" , description =
            "<strong> 회비 출금 내역 기록용 사진 조회<p></p>\n" +
            "<p><strong> budgetSeq</strong> : 모임 회계 PK 값 (long) </p>\n")
    @ApiResponse(description =
            "<p> ResponseCode 응답 코드 </p> " +
                    "<p>  1 : 성공 </p> " +
                    "<p>  2 : 실패, ResultMsg </p> " +
                    "<p>  99 : 알 수 없는 오류 발생 실패</p> ")
    public ResultDto<GetBudgetPicRes> getBudgetPic(@PathVariable(name = "budget_seq") long budgetSeq) {
        return ResultDto.resultDto(HttpStatus.OK, 1, GET_SUCCESS_MESSAGE, service.getBudgetPic(budgetSeq));
    }

    @DeleteMapping
    @Operation(summary = "회계 내역 삭제" , description =
            "<strong> 회비 입출금 내역 삭제 (모임장 or 회계 담당 멤버)<p></p>\n" +
            "<p><strong> budgetSeq</strong> : 모임 회계 PK 값 (long) </p>\n")
    @ApiResponse(description =
            "<p> ResponseCode 응답 코드 </p> " +
                    "<p>  1 : 성공 </p> " +
                    "<p>  2 : 실패, ResultMsg </p> " +
                    "<p>  99 : 알 수 없는 오류 발생 실패</p> ")
    public ResultDto<Long> deleteBudget(@RequestParam(name = "budget_seq") long budgetSeq) {
        service.deleteBudget(budgetSeq);
        return ResultDto.resultDto(HttpStatus.OK, 1, DELETE_SUCCESS_MESSAGE);
    }

    @GetMapping("/member")
    @Operation(summary = "멤버 별 회비 입금 내역 조회" , description =
    "<strong> 모임 멤버들의 회비 입금 통계. month 값은 '07', '12'와 같이 입력<p></p>\n" +
            "<p><strong> budgetPartySeq</strong> : 모임 마스터 PK 값 (long) </p>\n")
    @ApiResponse(description =
            "<p> ResponseCode 응답 코드 </p> " +
                    "<p>  1 : 성공 </p> " +
                    "<p>  2 : 실패, ResultMsg </p> " +
                    "<p>  99 : 알 수 없는 오류 발생 실패</p> ")
    public ResultDto<GetBudgetMemberRes> getBudgetMember(@RequestParam long budgetPartySeq, @RequestParam String month) {
        return ResultDto.resultDto(HttpStatus.OK, 1, GET_SUCCESS_MESSAGE, service.getBudgetMember(budgetPartySeq, month));
    }

    @GetMapping("/month")
    @Operation(summary = "월 별 정산 내역 출력" , description =
            "<strong> 월 별 입금, 출금 합계 및 통계 출력. month 값은 '07', '12'와 같이 입력<p></p>\n" +
            "<p><strong> budgetPartySeq</strong> : 모임 마스터 PK 값 (long) </p>\n")
    @ApiResponse(description =
            "<p> ResponseCode 응답 코드 </p> " +
                    "<p>  1 : 성공 </p> " +
                    "<p>  2 : 실패, ResultMsg </p> " +
                    "<p>  99 : 알 수 없는 오류 발생 실패</p> ")
    public ResultDto<GetBudgetMonthlyRes> getBudgetMonthly(@RequestParam long budgetPartySeq, @RequestParam String month) {
        return ResultDto.resultDto(HttpStatus.OK, 1, GET_SUCCESS_MESSAGE, service.getBudgetMonthly(budgetPartySeq, month));
    }

}