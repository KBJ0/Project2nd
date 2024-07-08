package com.green.project2nd.common;

import com.green.project2nd.planjoin.model.TogglePlanJoinReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckMapper {
    int checkPartySeq(Long partySeq);

    int checkMemberPartySeq(Long memberPartySeq);
    int checkMemberUserSeq(Long memberUserSeq);

    int checkJoinPartySeq(Long joinPartySeq);
    int checkUserPartySeq(Long joinUserSeq);

    int checkBudgetPartySeq(long budgetPartySeq);
    int checkBudgetSeq(long budgetSeq);

    int checkPlanSeq(long planSeq);
    int checkPlanPartySeq(long planPartySeq);

    int checkPlanJoin(TogglePlanJoinReq p);
}
