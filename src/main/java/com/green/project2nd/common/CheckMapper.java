package com.green.project2nd.common;

import com.green.project2nd.planjoin.model.TogglePlanJoinReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckMapper {
    //범준
    int checkPartyLeader(Long partySeq, Long userSeq);

    int checkPartySeq(Long partySeq);
    int checkUserSeq(Long userSeq);
    int checkPartyName(String partyName);

    int checkJoinApplicationOfUser(Long partySeq, Long userSeq);
    int checkMemberForPartySeqAndUserSeq(Long partySeq, Long userSeq);
    int checkPartyNowMem(Long partySeq);


    //예림
    int checkBudgetPartySeq(long budgetPartySeq);
    int checkBudgetSeq(long budgetSeq);
    int checkPlanSeq(long planSeq);
    int checkPlanPartySeq(long planPartySeq);
    int checkPlanJoin(TogglePlanJoinReq p);




}
