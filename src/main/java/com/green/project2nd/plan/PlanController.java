package com.green.project2nd.plan;

import com.green.project2nd.common.ResultDto;
import com.green.project2nd.plan.model.*;
import com.green.project2nd.planjoin.model.TogglePlanJoinReq;
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
@RequestMapping("/api/plan")
@Tag(name = "Plan (모임 일정 관리)", description = "plan CRUD")
public class PlanController {
    private final PlanService service;

    @PostMapping
    @Operation(summary = "모임 일정 등록" , description = "모임 내에서 열리는 일정 등록 (모임장)")
    public ResultDto<Integer> postPlan(@RequestBody PostPlanReq p){
        int result = service.postPlan(p);

        return ResultDto.<Integer>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(result).
                build();
    }

    @PatchMapping
    @Operation(summary = "모임 일정 수정" , description = "등록했던 일정 내용 수정 (모임장)")
    public ResultDto<Integer> patchPlan(@RequestBody PatchPlanReq p){
        int result = service.patchPlan(p);

        return ResultDto.<Integer>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(result).
                build();
    }

    @GetMapping("{plan_party_seq}")
    @Operation(summary = "모임 일정 전체 조회" , description = "등록되어 있는 일정 전체 출력")
    public ResultDto<List<GetPlanRes>> getPlanAll(@PathVariable(name = "plan_party_seq") long planPartySeq){
        List<GetPlanRes> result = service.getPlanAll(planPartySeq);

        return ResultDto.<List<GetPlanRes>>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(result).
                build();
    }
    @GetMapping("/party/{plan_seq}")
    @Operation(summary = "모임 일정 상세 조회" , description = "등록되어 있는 한 개의 일정 상세 출력")
    public ResultDto<GetPlanRes> getPlan(@PathVariable(name="plan_seq") long planSeq){
        GetPlanRes result = service.getPlan(planSeq);

        return ResultDto.<GetPlanRes>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(result).
                build();
    }

    @DeleteMapping("{plan_seq}")
    @Operation(summary = "모임 일정 삭제" , description = "등록했던 일정 삭제 (모임장)")
    public ResultDto<Integer> deletePlan(@PathVariable(name="plan_seq") long planSeq){
        int result = service.deletePlan(planSeq);

        return ResultDto.<Integer>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(result).
                build();
    }

}
