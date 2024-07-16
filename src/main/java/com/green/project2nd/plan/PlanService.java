package com.green.project2nd.plan;

import com.green.project2nd.common.CheckMapper;
import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.plan.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.green.project2nd.plan.exception.ConstMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanMapper mapper;
    private final CheckMapper checkMapper;

    public ResultDto<Integer> postPlan(PostPlanReq p) {
        if (checkMapper.checkPlanPartySeq(p.getPlanPartySeq()) == null) {
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST, 2, NOT_FOUND_PARTY);
        } else if (p.getPlanPartySeq() == null || p.getPlanStartDt() == null || p.getPlanStartTime() == null ||
                p.getPlanTitle() == null || p.getPlanContents() == null) {
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST, 2, NULL_ERROR_MESSAGE);
        } else {
            mapper.postPlan(p);
            return ResultDto.resultDto(HttpStatus.OK, 1, POST_SUCCESS_MESSAGE);
        }
    }

    public ResultDto<Integer> patchPlan(PatchPlanReq p) {
        if (checkMapper.checkPlanSeq(p.getPlanSeq()) == null) {
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST, 2, NOT_FOUND_PLAN);
        } else {
            mapper.patchPlan(p);
            return ResultDto.resultDto(HttpStatus.OK, 1, PATCH_SUCCESS_MESSAGE);
        }
    }

    public ResultDto<Integer> patchPlanCompleted(long planSeq) {
        if (checkMapper.checkPlanSeq(planSeq) == null) {
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST, 2, NOT_FOUND_PLAN);
        } else {
            mapper.patchPlanCompleted(planSeq);
            return ResultDto.resultDto(HttpStatus.OK, 1, PATCH_SUCCESS_MESSAGE);
        }
    }

    public ResultDto<List<GetPlanRes>> getPlanAll(long planPartySeq) {
        if (checkMapper.checkPlanPartySeq(planPartySeq) == null) {
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST, 2, NOT_FOUND_PARTY);
        } else {
            return ResultDto.resultDto(HttpStatus.OK, 1, GET_SUCCESS_MESSAGE, mapper.getPlanAll(planPartySeq));
        }
    }

    public ResultDto<GetPlanRes> getPlan(long planSeq) {
        if (checkMapper.checkPlanSeq(planSeq) == null) {
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST, 2, NOT_FOUND_PLAN);
        } else {
            return ResultDto.resultDto(HttpStatus.OK, 1, GET_SUCCESS_MESSAGE, mapper.getPlan(planSeq));
        }
    }

    public ResultDto<List<GetPlanMemberRes>> getPlanMember(long planSeq) {
        if (checkMapper.checkPlanSeq(planSeq) == null) {
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST, 2, NOT_FOUND_PLAN);
        } else {
            return ResultDto.resultDto(HttpStatus.OK, 1, GET_SUCCESS_MESSAGE, mapper.getPlanMember(planSeq));
        }
    }

    public ResultDto<Integer> deletePlan(long planSeq) {
        if (checkMapper.checkPlanSeq(planSeq) == null) {
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST, 2, NOT_FOUND_PLAN);
        } else {
            mapper.deletePlan(planSeq);
            return ResultDto.resultDto(HttpStatus.OK, 1, DELETE_SUCCESS_MESSAGE);
        }
    }
}
