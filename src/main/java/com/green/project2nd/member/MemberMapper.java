package com.green.project2nd.member;

import com.green.project2nd.member.model.GetMemberRes;
import com.green.project2nd.member.model.UpdateMemberReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
//    int postMember(PostMemberReq p);
    List<GetMemberRes> getMember(Long memberPartySeq);
    GetMemberRes getMemberDetail(Long memberPartySeq, Long memberUserSeq);
    int updateMember(UpdateMemberReq p);
    int updateMemberGb(Long memberPartySeq, Long memberUserSeq);
    int checkMemberGb(Long memberPartySeq, Long memberUserSeq);

//    int deleteMember(Long memberPartySeq, Long memberUserSeq);
}
