package com.green.project2nd.plan;

import com.green.project2nd.plan.exception.PlanExceptionHandler;
import com.green.project2nd.plan.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanMapper mapper;
    private final PlanExceptionHandler check;

    public int postPlan(PostPlanReq p) {
        return mapper.postPlan(p);
    }

    public int patchPlan(PatchPlanReq p) {
        return mapper.patchPlan(p);
    }

    public int patchPlanCompleted(long planSeq){
        check.exception(planSeq);
        return mapper.patchPlanCompleted(planSeq);
    }

    public List<GetPlanRes> getPlanAll(long planPartySeq) {
        check.exception2(planPartySeq);
        return mapper.getPlanAll(planPartySeq);
    }

    public GetPlanRes getPlan(long planSeq) {
        check.exception(planSeq);
        return mapper.getPlan(planSeq);
    }

    public List<GetPlanMemberRes> getPlanMember(long planSeq){
        check.exception(planSeq);
        return mapper.getPlanMember(planSeq);
    }

    public int deletePlan(long planSeq) {
        check.exception(planSeq);
        return mapper.deletePlan(planSeq);
    }
}
