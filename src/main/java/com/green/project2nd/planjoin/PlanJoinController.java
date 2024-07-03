package com.green.project2nd.planjoin;

import com.green.project2nd.common.ResultDto;
import com.green.project2nd.plan.model.GetPlanRes;
import com.green.project2nd.plan.model.PatchPlanReq;
import com.green.project2nd.plan.model.PostPlanReq;
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
@RequestMapping("/api/plan/join")
@Tag(name = "Plan Join (모임 일정 참가 관리)", description = "plan join CRUD")
public class PlanJoinController {
    private final PlanJoinService service;

    @PostMapping
    @Operation(summary = "모임 일정 참가 신청" , description = "등록되어 있는 일정에 참가 신청 (모임장 or 모임 멤버)")
    public ResultDto<Integer> postPlanJoin(@RequestBody TogglePlanJoinReq p){
        int result = service.postPlanJoin(p);

        return ResultDto.<Integer>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(result).
                build();
    }

    @DeleteMapping
    @Operation(summary = "모임 일정 참가 취소" , description = "등록되어 있는 일정에 참가 취소 신청 (모임장 or 모임 멤버)")
    public ResultDto<Integer> deletePlanJoin(@RequestBody TogglePlanJoinReq p){
        int result = service.deletePlanJoin(p);

        return ResultDto.<Integer>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(result).
                build();
    }
}
