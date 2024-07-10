package com.green.project2nd.join;

import com.green.project2nd.join.model.GetJoinRes;
import com.green.project2nd.join.model.PostJoinReq;
import com.green.project2nd.join.model.UpdateJoinGbReq;
import com.green.project2nd.join.model.UpdateJoinReq;
import com.green.project2nd.member.model.GetMemberRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JoinMapper {
    int postJoin(PostJoinReq p);

    List<GetJoinRes> getJoin(Long joinPartySeq);
    GetJoinRes getJoinDetail(Long joinPartySeq, Long joinUserSeq);

    int updateJoin(UpdateJoinReq p);
    int updateJoinGb(UpdateJoinGbReq p);
    void postMember(Long joinPartySeq, Long joinUserSeq);

    int deleteJoin(Long joinPartySeq, Long joinUserSeq);
}