package com.green.project2nd.planjoin;

import com.green.project2nd.planjoin.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanJoinMapper {
    int postPlanJoin(TogglePlanJoinReq p);
    int deletePlanJoin(TogglePlanJoinReq p);

    GetMemberSeqRes getMemberSeq(long memberSeq);
}
