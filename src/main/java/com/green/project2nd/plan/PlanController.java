package com.green.project2nd.plan;

import com.green.project2nd.common.ResultDto;
import com.green.project2nd.plan.model.*;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "모임 일정 등록" , description = "모임 내에서 열리는 일정 등록 (모임장)")
    public ResultDto<Integer> postPlan(@RequestBody PostPlanReq p){
        if (p == null) throw new NullPointerException(NULL_ERROR_MESSAGE);
        int result = service.postPlan(p);

        return ResultDto.<Integer>builder().
                statusCode(HttpStatus.OK).
                resultMsg(POST_SUCCESS_MESSAGE).
                resultData(result).
                build();
    }

    @PatchMapping
    @Operation(summary = "모임 일정 수정" , description = "등록했던 일정 내용 수정 (모임장)")
    public ResultDto<Integer> patchPlan(@RequestBody PatchPlanReq p){
        if (p == null) throw new NullPointerException(NULL_ERROR_MESSAGE);
        int result = service.patchPlan(p);

        return ResultDto.<Integer>builder().
                statusCode(HttpStatus.OK).
                resultMsg(PATCH_SUCCESS_MESSAGE).
                resultData(result).
                build();
    }

    @PatchMapping("{plan_seq}")
    @Operation(summary = "모임 일정 완료" , description = "종료된 일정 완료 처리 (모임장)")
    public ResultDto<Integer> patchPlanCompleted(@PathVariable(name="plan_seq") long planSeq){
        int result = service.patchPlanCompleted(planSeq);

        return ResultDto.<Integer>builder().
                statusCode(HttpStatus.OK).
                resultMsg(PATCH_SUCCESS_MESSAGE).
                resultData(result).
                build();
    }

    @GetMapping
    @Operation(summary = "모임 일정 전체 조회" , description = "등록되어 있는 일정 전체 출력")
    public ResultDto<List<GetPlanRes>> getPlanAll(@RequestParam(name = "plan_party_seq") long planPartySeq){
        List<GetPlanRes> result = service.getPlanAll(planPartySeq);

        return ResultDto.<List<GetPlanRes>>builder().
                statusCode(HttpStatus.OK).
                resultMsg(GET_SUCCESS_MESSAGE).
                resultData(result).
                build();
    }

    @GetMapping("{plan_seq}")
    @Operation(summary = "모임 일정 상세 조회" , description = "등록되어 있는 한 개의 일정 상세 출력")
    public ResultDto<GetPlanRes> getPlan(@PathVariable(name="plan_seq") long planSeq){
        GetPlanRes result = service.getPlan(planSeq);

        return ResultDto.<GetPlanRes>builder().
                statusCode(HttpStatus.OK).
                resultMsg(GET_SUCCESS_MESSAGE).
                resultData(result).
                build();
    }

    @GetMapping("/member")
    @Operation(summary = "일정 참가 멤버 조회" , description = "해당 모임에 참여하는 모임 멤버들 출력")
    public ResultDto<List<GetPlanMemberRes>> getPlanMember(@RequestParam long planSeq){
        List<GetPlanMemberRes> result = service.getPlanMember(planSeq);

        return ResultDto.<List<GetPlanMemberRes>>builder().
                statusCode(HttpStatus.OK).
                resultMsg(GET_SUCCESS_MESSAGE).
                resultData(result).
                build();
    }

    @DeleteMapping
    @Operation(summary = "모임 일정 삭제" , description = "등록했던 일정 삭제 (모임장)")
    public ResultDto<Integer> deletePlan(@RequestParam(name="plan_seq") long planSeq){
        int result = service.deletePlan(planSeq);

        return ResultDto.<Integer>builder().
                statusCode(HttpStatus.OK).
                resultMsg(DELETE_SUCCESS_MESSAGE).
                resultData(result).
                build();
    }

}
