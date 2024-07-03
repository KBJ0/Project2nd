package com.green.project2nd.common;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckMapper {
    int checkPartySeq(Long partySeq);

    int checkMemberPartySeq(Long memberPartySeq);
    int checkMemberUserSeq(Long memberUserSeq);

    int checkJoinPartySeq(Long joinPartySeq);
    int checkUserPartySeq(Long joinUserSeq);
}
