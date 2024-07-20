package com.green.project2nd.member;


import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.member.exception.MemberExceptionHandler;
import com.green.project2nd.member.model.GetMemberRes;
import com.green.project2nd.member.model.UpdateMemberReq;
import com.green.project2nd.member.model.UpdateMemberRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {
    private final MemberMapper mapper;
    private final MemberExceptionHandler check;

    //모임의 멤버들의 정보 불러오기
    public ResultDto<List<GetMemberRes>> getMember(Long memberPartySeq) {
        // 1.존재하는 모임인가?
        check.exceptionParty(memberPartySeq);
        // 멤버 정보들 return
        return ResultDto.resultDto(HttpStatus.OK,1, " 멤버들의 정보를 불러왔습니다.", mapper.getMember(memberPartySeq));
    }

    //모임의 멤버 한명의 정보 불러오기
    public ResultDto<GetMemberRes> getMemberDetail(Long memberPartySeq, Long memberUserSeq) {
        // 1.존재하는 모임인가?, 2.존재하는 유저인가?
        check.exception(memberPartySeq, memberUserSeq);
        // 멤버 한명 정보들 return
        return ResultDto.resultDto(HttpStatus.OK,1, "멤버 한명의 정보를 불러왔습니다."
                , mapper.getMemberDetail(memberPartySeq, memberUserSeq));
    }

    //멤버 역할 수정(3차때 사용)
    public ResultDto<UpdateMemberRes> updateMember(Long memberPartySeq, UpdateMemberReq p) {
        // 1.존재하는 모임인가?, 2.존재하는 유저인가?
        // 3.존재하는 멤버인가?
        check.exception(memberPartySeq, p);
        // 멤버 역할 수정
        p.setMemberPartySeq(memberPartySeq);
        mapper.updateMember(p);
        return ResultDto.resultDto(HttpStatus.OK,1, "멤버 권한을 수정하였습니다.");
    }

    //멤버 차단 (모임장이 멤버 권한을 수정함)
    public ResultDto<UpdateMemberRes> updateMemberGb(Long memberPartySeq, Long memberUserSeq, Long memberLeaderUserSeq) {
        // 1.존재하는 모임인가?, 2.존재하는 유저인가?
        // 3.존재하는 멤버인가?
        check.exception(memberPartySeq, memberUserSeq);
        // 1.존재하는 모임인가?, 2.존재하는 유저인가?
        // 3.존재하는 멤버인가?, 4.수정자가 모임장이 맞는가?
        check.exceptionLeader(memberPartySeq, memberLeaderUserSeq);
        // 멤버의 권한을 수정함 ( memberRole,Gb = 0 )
        mapper.updateMemberGb(memberPartySeq, memberUserSeq);
        return ResultDto.resultDto(HttpStatus.OK,1, "해당 멤버를 차단하였습니다.");
    }
}
