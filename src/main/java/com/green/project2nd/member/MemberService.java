package com.green.project2nd.member;


import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.member.exception.MemberExceptionHandler;
import com.green.project2nd.member.model.GetMemberRes;
import com.green.project2nd.member.model.UpdateMemberReq;
import com.green.project2nd.member.model.UpdateMemberRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {
    private final MemberMapper mapper;
    private final MemberExceptionHandler check;

//    public ResultDto<Integer> postMember(Long memberPartySeq, Long memberUserSeq) {
//        check.exception(memberPartySeq, memberUserSeq);
//        PostMemberReq req = new PostMemberReq();
//        req.setMemberSeq(memberPartySeq);
//        return ResultDto.resultDto(1, " 멤버 등록.", mapper.postMember(req));
//    }

    public ResultDto<List<GetMemberRes>> getMember(Long memberPartySeq) {
        check.exceptionParty(memberPartySeq);
        return ResultDto.resultDto(1, " 멤버들의 정보를 불러왔습니다.", mapper.getMember(memberPartySeq));
    }

    public ResultDto<GetMemberRes> getMemberDetail(Long memberPartySeq, Long memberUserSeq) {
        check.exception(memberPartySeq, memberUserSeq);
        return ResultDto.resultDto(1, "멤버 한명의 정보를 불러왔습니다."
                , mapper.getMemberDetail(memberPartySeq, memberUserSeq));
    }

    public ResultDto<UpdateMemberRes> updateMember(Long memberPartySeq, UpdateMemberReq p) {
        check.exception(memberPartySeq, p);
        p.setMemberPartySeq(memberPartySeq);
        mapper.updateMember(p);
        return ResultDto.resultDto(1, "멤버 권한을 수정하였습니다.");
    }

//    public ResultDto<UpdateMemberRes> updateMemberGb(Long memberPartySeq, Long memberUserSeq) {
//        check.exception(memberPartySeq, memberUserSeq);
//        mapper.updateMemberGb(memberPartySeq, memberUserSeq);
//        if (mapper.checkMemberGb(memberPartySeq,memberUserSeq) == 0) {
//            return ResultDto.resultDto(1, "멤버 차단 완료.");
//        }
//        return ResultDto.resultDto(1, "멤버 차단 해지 완료.");
//    }

    public ResultDto<UpdateMemberRes> updateMemberGb(Long memberPartySeq, Long memberUserSeq, Long memberLeaderUserSeq) {
        check.exception(memberPartySeq, memberUserSeq);
        check.exceptionLeader(memberPartySeq, memberLeaderUserSeq);
        mapper.updateMemberGb(memberPartySeq, memberUserSeq);
        if (mapper.checkMemberGb(memberPartySeq, memberUserSeq) == 0) {
            return ResultDto.resultDto(1, "해당 멤버를 차단하였습니다.");
        }
        return ResultDto.resultDto(1, "해당 멤버의 차단을 해지하였습니다.");
    }

//    public ResultDto<Integer> deleteMember(Long memberPartySeq, Long memberUserSeq) {
//        check.exception(memberPartySeq, memberUserSeq);
//        mapper.deleteMember(memberPartySeq, memberUserSeq);
//        return ResultDto.resultDto(1, "해당 멤버를 삭제했습니다.");
//    }

}
