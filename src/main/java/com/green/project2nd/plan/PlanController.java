package com.green.project2nd.plan;

import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.plan.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.green.project2nd.plan.exception.ConstMessage.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/plan")
@Tag(name = "Plan (모임 일정 관리)", description = "plan CRUD")
public class PlanController {
    private final PlanService service;

    @PostMapping
    @Operation(summary = "모임 일정 등록" , description =
            "<strong> 모임 내에서 열리는 일정 등록 (모임장)<p></p>\n" +
            "<p><strong> planPartySeq</strong> : 모임 마스터 PK 값 (long) </p>\n" +
            "<p><strong> planStartDt</strong> : 모임 일정 시작 날짜 (String) </p>\n"+
            "<p><strong> planStartTime</strong> : 모임 일정 시작 시간 (String) </p>\n"+
            "<p><strong> planLocation</strong> : 모임 장소 (String) (NULL 허용)</p>\n"+
            "<p><strong> planTitle</strong> : 일정 제목 (String) </p>\n"+
            "<p><strong> planContents</strong> : 일정 내용 (String) </p>\n")
    @ApiResponse(description =
            "<p> ResponseCode 응답 코드 </p> " +
                    "<p>  1 : 성공 </p> " +
                    "<p>  2 : 실패, ResultMsg </p> " +
                    "<p>  99 : 알 수 없는 오류 발생 실패</p> ")
    public ResultDto<Integer> postPlan(@RequestBody PostPlanReq p) {
        if (p == null) throw new NullPointerException(NULL_ERROR_MESSAGE);
        service.postPlan(p);
        return ResultDto.resultDto(HttpStatus.OK, 1, POST_SUCCESS_MESSAGE);
    }

    @PatchMapping
    @Operation(summary = "모임 일정 수정" , description =
            "<strong> 등록했던 일정 내용 수정 (모임장). 수정할 부분만 입력해도 가능<p></p>\n" +
                    "<p><strong> planSeq</strong> : 모임 일정 PK 값 (long) </p>\n" +
                    "<p><strong> planStartDt</strong> : 모임 일정 시작 날짜 (String) </p>\n"+
                    "<p><strong> planStartTime</strong> : 모임 일정 시작 시간 (String) </p>\n"+
                    "<p><strong> planLocation</strong> : 모임 장소 (String) (NULL 허용)</p>\n"+
                    "<p><strong> planTitle</strong> : 일정 제목 (String) </p>\n"+
                    "<p><strong> planContents</strong> : 일정 내용 (String) </p>\n")
    @ApiResponse(description =
            "<p> ResponseCode 응답 코드 </p> " +
                    "<p>  1 : 성공 </p> " +
                    "<p>  2 : 실패, ResultMsg </p> " +
                    "<p>  99 : 알 수 없는 오류 발생 실패</p> ")
    public ResultDto<Integer> patchPlan(@RequestBody PatchPlanReq p){
        if (p == null) throw new NullPointerException(NULL_ERROR_MESSAGE);
        service.patchPlan(p);
        return ResultDto.resultDto(HttpStatus.OK, 1, PATCH_SUCCESS_MESSAGE);
    }

    @PatchMapping("{plan_seq}")
    @Operation(summary = "모임 일정 완료" , description =
            "<strong> 종료된 일정 완료 처리 (모임장)<p></p>\n" +
                    "<p><strong> planSeq</strong> : 모임 일정 PK 값 (long) </p>\n")
    @ApiResponse(description =
            "<p> ResponseCode 응답 코드 </p> " +
                    "<p>  1 : 성공 </p> " +
                    "<p>  2 : 실패, ResultMsg </p> " +
                    "<p>  99 : 알 수 없는 오류 발생 실패</p> ")
    public ResultDto<Integer> patchPlanCompleted(@PathVariable(name="plan_seq") long planSeq){
        service.patchPlanCompleted(planSeq);
        return ResultDto.resultDto(HttpStatus.OK, 1, PATCH_SUCCESS_MESSAGE);
    }

    @GetMapping
    @Operation(summary = "모임 일정 전체 조회" , description =
            "<strong> 등록되어 있는 일정 전체 출력<p></p>\n" +
                    "<p><strong> planPartySeq</strong> : 모임 마스터 PK 값 (long) </p>\n")
    @ApiResponse(description =
            "<p> ResponseCode 응답 코드 </p> " +
                    "<p>  1 : 성공 </p> " +
                    "<p>  2 : 실패, ResultMsg </p> " +
                    "<p>  99 : 알 수 없는 오류 발생 실패</p> ")
    public ResultDto<List<GetPlanRes>> getPlanAll(@RequestParam(name = "plan_party_seq") long planPartySeq){
        return ResultDto.resultDto(HttpStatus.OK, 1, GET_SUCCESS_MESSAGE, service.getPlanAll(planPartySeq));
    }

    @GetMapping("{plan_seq}")
    @Operation(summary = "모임 일정 상세 조회" , description =
            "<strong> 등록되어 있는 한 개의 일정 상세 출력 <p></p>\n" +
                    "<p><strong> planSeq</strong> : 모임 일정 PK 값 (long) </p>\n")
    @ApiResponse(description =
            "<p> ResponseCode 응답 코드 </p> " +
                    "<p>  1 : 성공 </p> " +
                    "<p>  2 : 실패, ResultMsg </p> " +
                    "<p>  99 : 알 수 없는 오류 발생 실패</p> ")
    public ResultDto<GetPlanRes> getPlan(@PathVariable(name="plan_seq") long planSeq){
        return ResultDto.resultDto(HttpStatus.OK, 1, GET_SUCCESS_MESSAGE, service.getPlan(planSeq));
    }

    @GetMapping("/member")
    @Operation(summary = "일정 참가 멤버 조회" , description =
            "<strong> 해당 모임에 참여하는 모임 멤버들 출력 <p></p>\n" +
            "<p><strong> planSeq</strong> : 모임 일정 PK 값 (long) </p>\n")
    @ApiResponse(description =
            "<p> ResponseCode 응답 코드 </p> " +
                    "<p>  1 : 성공 </p> " +
                    "<p>  2 : 실패, ResultMsg </p> " +
                    "<p>  99 : 알 수 없는 오류 발생 실패</p> ")
    public ResultDto<List<GetPlanMemberRes>> getPlanMember(@RequestParam long planSeq){
        return ResultDto.resultDto(HttpStatus.OK, 1, GET_SUCCESS_MESSAGE, service.getPlanMember(planSeq));
    }

    @DeleteMapping
    @Operation(summary = "모임 일정 삭제" , description =
            "<strong> 등록했던 일정 삭제 (모임장) <p></p>\n" +
                    "<p><strong> planSeq</strong> : 모임 일정 PK 값 (long) </p>\n")
    @ApiResponse(description =
            "<p> ResponseCode 응답 코드 </p> " +
                    "<p>  1 : 성공 </p> " +
                    "<p>  2 : 실패, ResultMsg </p> " +
                    "<p>  99 : 알 수 없는 오류 발생 실패</p> ")
    public ResultDto<Integer> deletePlan(@RequestParam(name="plan_seq") long planSeq){
        service.deletePlan(planSeq);
        return ResultDto.resultDto(HttpStatus.OK, 1, DELETE_SUCCESS_MESSAGE);
    }
}
