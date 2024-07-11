package com.green.project2nd.join;


import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.join.exception.JoinExceptionHandler;
import com.green.project2nd.join.model.GetJoinRes;
import com.green.project2nd.join.model.PostJoinReq;
import com.green.project2nd.join.model.UpdateJoinGbReq;
import com.green.project2nd.join.model.UpdateJoinReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class JoinService {
    private final JoinMapper mapper;
    private final JoinExceptionHandler check;


    public ResultDto<Integer> postJoin(Long joinPartySeq, PostJoinReq p) {
        check.exception(joinPartySeq,p);
        p.setJoinPartySeq(joinPartySeq);
        mapper.postJoin(p);
        return ResultDto.resultDto(1, " 모임 가입신청을 하였습니다");
    }

    public ResultDto<List<GetJoinRes>> getJoin(Long joinPartySeq, Long leaderUserSeq) {
        check.exceptionLeader(joinPartySeq,leaderUserSeq);
        return ResultDto.resultDto(1, " 모임 신청서들을 불러옵니다.", mapper.getJoin(joinPartySeq));

    }
    public ResultDto<GetJoinRes> getJoinDetail(Long joinPartySeq, Long joinUserSeq) {
        check.exception(joinPartySeq, joinUserSeq);
        return ResultDto.resultDto(1, " 모임 신청서를 불러옵니다.", mapper.getJoinDetail(joinPartySeq, joinUserSeq));
    }

    public ResultDto<Integer> updateJoin(Long joinPartySeq, UpdateJoinReq p) {
        p.setJoinPartySeq(joinPartySeq);
        check.exception(joinPartySeq, p.getJoinUserSeq());


        return ResultDto.resultDto(1, " 모임 신청서를 수정합니다.", mapper.updateJoin(p));
    }
    public ResultDto<Integer> updateJoinGb(Long joinPartySeq, UpdateJoinGbReq p) {
        p.setJoinPartySeq(joinPartySeq);
        check.exception(joinPartySeq, p);
        mapper.updateJoinGb(p);
        //신청서 거절
        if(p.getJoinGb()==2){return ResultDto.resultDto(1, " 신청서를 거절하였습니다. (2:거절)");}

        //이미 있는 유저는 멤버의 권한을 exceptionMember에서 확인하고 수정함.
        check.exceptionMember(joinPartySeq,p.getJoinUserSeq());

        //없는 멤버는 등록함.
        mapper.postMember(joinPartySeq,p.getJoinUserSeq());
        return ResultDto.resultDto(1, " 신청서를 승인하였습니다. (1: 멤버등록)");
    }

    public ResultDto<Integer> deleteJoin(Long joinPartySeq, Long joinUserSeq) {
        check.exception(joinPartySeq, joinUserSeq);
        return ResultDto.resultDto(1, " 모임 신청서를 삭제합니다.", mapper.deleteJoin(joinPartySeq, joinUserSeq));
    }


}
