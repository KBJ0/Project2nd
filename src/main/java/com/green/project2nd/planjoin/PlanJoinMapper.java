package com.green.project2nd.planjoin;

import com.green.project2nd.plan.model.GetPlanRes;
import com.green.project2nd.plan.model.PatchPlanReq;
import com.green.project2nd.plan.model.PostPlanReq;
import com.green.project2nd.planjoin.model.TogglePlanJoinReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanJoinMapper {
    int postPlanJoin(TogglePlanJoinReq p);
    int deletePlanJoin(TogglePlanJoinReq p);
}
