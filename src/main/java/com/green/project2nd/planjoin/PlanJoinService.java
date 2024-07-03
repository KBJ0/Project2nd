package com.green.project2nd.planjoin;

import com.green.project2nd.plan.model.GetPlanRes;
import com.green.project2nd.plan.model.PatchPlanReq;
import com.green.project2nd.plan.model.PostPlanReq;
import com.green.project2nd.planjoin.model.TogglePlanJoinReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanJoinService {
    private final PlanJoinMapper mapper;

    public int postPlanJoin(TogglePlanJoinReq p){
        return mapper.postPlanJoin(p);
    };

    public int deletePlanJoin(TogglePlanJoinReq p){
        return mapper.deletePlanJoin(p);
    };
}
