package com.green.project2nd.party;

import com.green.project2nd.party.model.GetPartyRes;
import com.green.project2nd.party.model.PostPartyReq;
import com.green.project2nd.party.model.UpdatePartyReq;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PartyMapper {
    int postParty(PostPartyReq p);

    int checkPartySeq(Long partySeq);
    List<GetPartyRes> getParty();
    GetPartyRes getPartyDetail(Long partySeq);

    int updateParty(UpdatePartyReq p);
    int updatePartyAuthGb(Long partySeq, int partyAuthGb);

    int checkMemberRole(Long partySeq);
    int deleteParty(Long partySeq);
}
