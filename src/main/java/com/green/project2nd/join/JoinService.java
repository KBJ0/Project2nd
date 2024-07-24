package com.green.project2nd.join;


import com.green.project2nd.common.CheckMapper;
import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.common.myexception.ReturnDto;
import com.green.project2nd.join.exception.JoinExceptionHandler;
import com.green.project2nd.join.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.green.project2nd.plan.exception.ConstMessage.NOT_FOUND_PLAN;

@RequiredArgsConstructor
@Service
@Slf4j
public class JoinService {
    private final JoinMapper mapper;
    private final CheckMapper checkMapper;
    private final JoinExceptionHandler check;

    //모임 신청서 작성 (모임 가입을 위한 신청서 작성)
    public ResultDto<Integer> postJoin(Long joinPartySeq, PostJoinReq p) {
        // 1.존재하는 모임인가?, 2.존재하는 유저인가?
        // 3.이미 모임원인 유저가 신청하는가?(모임장 포함)
        // 4.이미 신청한 모임인가?
        check.exception(joinPartySeq,p);
        // 모임 신청서 작성
        p.setJoinPartySeq(joinPartySeq);
        mapper.postJoin(p);
        return ResultDto.resultDto(HttpStatus.OK,1, " 모임 가입신청을 하였습니다");
    }

    //모임 신청서들 불러오기 (모임장이 신청된 신청서들 확인용)
    public ResultDto<List<GetJoinRes>> getJoin(Long joinPartySeq, Long leaderUserSeq) {
        // 1.존재하는 모임인가?, 2.존재하는 유저인가?
        // 3.불러오는 유저가 모임장이 맞는가?
        check.exceptionLeader(joinPartySeq,leaderUserSeq);
        // 모임 신청서들 정보 return
        return ResultDto.resultDto(HttpStatus.OK,1, " 모임 신청서들을 불러옵니다.", mapper.getJoin(joinPartySeq));
    }

    //모임 신청서 하나 불러오기 (신청한 유저가 자신의 신청서 확인용)
    public ResultDto<GetJoinRes> getJoinDetail(Long joinPartySeq, Long joinUserSeq) {
        // 1.존재하는 모임인가?, 2.존재하는 유저인가?
        // 3.존재하는 신청서인가?
        check.exception(joinPartySeq, joinUserSeq);
        // 자신의 신청서 정보 return
        return ResultDto.resultDto(HttpStatus.OK,1, " 모임 신청서를 불러옵니다.", mapper.getJoinDetail(joinPartySeq, joinUserSeq));
    }

    //모임 신청서 내용 수정하기 (신청한 유저가 자신의 신청서의 내용을 수정함)
    public ResultDto<Integer> updateJoin(Long joinPartySeq, UpdateJoinReq p) {
        // 1.존재하는 모임인가?, 2.존재하는 유저인가?
        // 3.존재하는 신청서인가?
        p.setJoinPartySeq(joinPartySeq);
        check.exception(joinPartySeq, p.getJoinUserSeq());
        // 모임 신청서 수정
        mapper.updateJoin(p);
        return ResultDto.resultDto(HttpStatus.OK,1, " 모임 신청서를 수정합니다.");
    }

    //모임 신청서 승인 + 멤버 등록 or 차단된 멤버 권한 수정
    //(모임장이 신청서 승인시 신청서 정보와 멤버 정보가 추가 or 수정됨)
    //흐름도 : 에러 확인 -> 신청서 거절함? -> 이전에 추방당한 멤버임? -> 새로운 멤버 등록자임?
    public ResultDto<Integer> updateJoinGb(Long joinPartySeq, UpdateJoinGbReq p) {
        // 1.존재하는 모임인가?, 2.존재하는 유저인가?
        // 3.존재하는 신청서인가? 4.수정자가 모임장이 맞는가?
        // 5.현재 모임원의 수가 최대 모임원의 수인가?
        p.setJoinPartySeq(joinPartySeq);
        check.exception(joinPartySeq, p);
        // 신청서 수정 (getJoinGb가 1:승인, 2:거절)
        mapper.updateJoinGb(p);
        // 모임장이 신청서를 거절함.
        if(p.getJoinGb()==2){return ResultDto.resultDto(HttpStatus.OK,1, " 신청서를 거절하였습니다.");}
        // if = 추방당한 유저인지 확인, 차단 당한 멤버 정보를 수정함(DB 참조값), 신청서를 승인함.
        if(checkMapper.checkMemberForPartySeqAndUserSeq(joinPartySeq, p.getJoinUserSeq()) == 1){
            mapper.updateSuspendedMember(joinPartySeq,p.getJoinUserSeq());
            throw new ReturnDto("1,신청서를 승인하였습니다. (1: 멤버등록)");}
        // 신청서를 작성한 유저를 멤버로 등록함.
        mapper.postMember(joinPartySeq,p.getJoinUserSeq());
        return ResultDto.resultDto(HttpStatus.OK,1, " 신청서를 승인하였습니다.");
    }

    //모임 신청서 삭제 (신청한 유저가 자신의 신청서를 삭제함)
    public ResultDto<Integer> deleteJoin(Long joinPartySeq, Long joinUserSeq) {
        // 1.존재하는 모임인가?, 2.존재하는 유저인가?
        // 3.존재하는 신청서인가?
        check.exception(joinPartySeq, joinUserSeq);
        return ResultDto.resultDto(HttpStatus.OK,1, " 모임 신청서를 삭제합니다.", mapper.deleteJoin(joinPartySeq, joinUserSeq));
    }

    public ResultDto<List<GetMyJoinRes>> getMyJoin(long userSeq){
        if(checkMapper.checkUserSeq(userSeq) == 0){
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST, 2, "존재하지 않는 유저입니다.");
        }
        return ResultDto.resultDto(HttpStatus.OK,1, " 자신의 모임 신청서를 모두 불러옵니다.", mapper.getMyJoin(userSeq));
    }

}
