package com.green.project2nd.project2nd.member;


import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.member.MemberMapper;
import com.green.project2nd.member.exception.MemberExceptionHandler;
import com.green.project2nd.member.model.GetMemberRes;
import com.green.project2nd.member.model.PostMemberReq;
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

    public ResultDto<Integer> postMember(Long memberPartySeq, Long memberUserSeq) {
        check.exception(memberPartySeq, memberUserSeq);
        PostMemberReq req = new PostMemberReq();
        req.setMemberSeq(memberPartySeq);
        return ResultDto.resultDto("SU", " 멤버 등록.", mapper.postMember(req));
    }

    public ResultDto<List<GetMemberRes>> getMember(Long memberPartySeq) {
        check.exceptionMember(memberPartySeq);
        return ResultDto.resultDto("SU", " 멤버들 불러오기.", mapper.getMember(memberPartySeq));
    }
    public ResultDto<GetMemberRes> getMemberDetail(Long memberPartySeq, Long memberUserSeq) {
        check.exception(memberPartySeq,memberUserSeq);
        return ResultDto.resultDto("SU", "멤버 한명 불러오기."
                , mapper.getMemberDetail(memberPartySeq,memberUserSeq));
    }

    public ResultDto<UpdateMemberRes> updateMember(Long memberPartySeq, UpdateMemberReq p) {
        check.exception(memberPartySeq, p);
        p.setMemberPartySeq(memberPartySeq);
        mapper.updateMember(p);
        return ResultDto.resultDto("SU", "멤버 권한 수정.");
    }
    public ResultDto<UpdateMemberRes> updateMemberGb(Long memberPartySeq, Long memberUserSeq) {
        check.exception(memberPartySeq, memberUserSeq);
        int check = mapper.updateMemberGb(memberPartySeq,memberUserSeq);
        //0 = 활성화, 1는 비활성화
        if(check == 1){return ResultDto.resultDto("SU", "멤버 차단 완료.");}
        return ResultDto.resultDto("SU", "멤버 차단 해지 완료.");
    }

    public ResultDto<Integer> deleteMember(Long memberPartySeq, Long memberUserSeq) {
        check.exception(memberPartySeq, memberUserSeq);
        return ResultDto.resultDto("SU", " 멤버들 불러오기.", mapper.deleteMember(memberPartySeq,memberUserSeq));
    }
}
