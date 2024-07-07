package com.green.project2nd.plan;

import com.green.project2nd.plan.model.*;
import com.green.project2nd.planjoin.model.TogglePlanJoinReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanMapper mapper;

    public int postPlan(PostPlanReq p) {
        return mapper.postPlan(p);
    }

    public int patchPlan(PatchPlanReq p) {
        return mapper.patchPlan(p);
    }

    public List<GetPlanRes> getPlanAll(long planPartySeq) {
        return mapper.getPlanAll(planPartySeq);
    }

    public GetPlanRes getPlan(long planSeq) {
        return mapper.getPlan(planSeq);
    }

    public int deletePlan(long planSeq) {
        return mapper.deletePlan(planSeq);
    }
}
