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
    Integer checkBudgetPartySeq(Long budgetPartySeq);
    Integer checkBudgetMemberSeq(Long memberPartySeq, Long memberSeq);
    Integer checkBudgetSeq(Long budgetSeq);
    Integer checkPlanSeq(Long planSeq);
    Integer checkPlanPartySeq(Long planPartySeq);
    Integer checkPlanJoin(TogglePlanJoinReq p);
}
