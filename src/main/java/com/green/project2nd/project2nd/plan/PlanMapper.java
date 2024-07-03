package com.green.project2nd.project2nd.plan;

import com.green.project2nd.plan.model.GetPlanRes;
import com.green.project2nd.plan.model.PatchPlanReq;
import com.green.project2nd.plan.model.PostPlanReq;
import com.green.project2nd.planjoin.model.TogglePlanJoinReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanMapper {
    int postPlan(PostPlanReq p);
    int patchPlan(PatchPlanReq p);
    List<GetPlanRes> getPlanAll();
    GetPlanRes getPlan(long planSeq);
    int deletePlan(long planSeq);
    int postPlanJoin(TogglePlanJoinReq p);
    int deletePlanJoin(TogglePlanJoinReq p);
}
