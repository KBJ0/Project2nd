package com.green.project2nd.join.exception;

import com.green.project2nd.common.CheckMapper;
import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.common.myexception.MsgException;
import com.green.project2nd.common.myexception.MsgExceptionNull;
import com.green.project2nd.common.myexception.NullReqValue;
import com.green.project2nd.common.myexception.ReturnDto;
import com.green.project2nd.join.JoinMapper;
import com.green.project2nd.join.model.PostJoinReq;
import com.green.project2nd.join.model.UpdateJoinGbReq;
import com.green.project2nd.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@Order(1)
@RestControllerAdvice(basePackages = "com.green.project2nd.join")
public class JoinExceptionHandler {
    private final CheckMapper mapper;
    private final JoinMapper joinMapper;

    //C
    public void exception(Long partySeq,PostJoinReq p) {
        exceptionParty(partySeq);
        exceptionUser(p.getJoinUserSeq());
        if (mapper.checkPartyLeader(partySeq, p.getJoinUserSeq()) != 0){
            throw new MsgException("2,권한이 없는 유저입니다.");}
        if (mapper.checkMemberForPartySeqAndUserSeq(partySeq,p.getJoinUserSeq()) != 0) {
            joinMapper.deleteJoin(partySeq,p.getJoinUserSeq());}
        if (mapper.checkJoinApplicationOfUser(partySeq,p.getJoinUserSeq()) != 0) {
            throw new ReturnDto("2,이미 신청한 모임입니다.");}
    }
    // U2
    public void exceptionMember(Long partySeq, Long userSeq) {
        if (mapper.checkMemberForPartySeqAndUserSeq(partySeq, userSeq) == 1){
            joinMapper.updateSuspendedMember(partySeq,userSeq);
            throw new ReturnDto("1,신청서를 승인하였습니다. (1: 멤버등록)");}
    }
    //U2
    public void exception(Long partySeq, UpdateJoinGbReq p) {
        exception(partySeq, p.getJoinUserSeq());
        exceptionLeader(partySeq, p.getLeaderUserSeq());
        if (p.getJoinGb() == 1 && mapper.checkPartyNowMem(partySeq) == 1){
            throw new MsgException("2,승인이 실패되었습니다. (모임인원수가 최대입니다)");
        }
    }
    //R2 U1
    public void exception(Long partySeq,Long userSeq) {
        exceptionParty(partySeq);
        exceptionUser(userSeq);
        if (mapper.checkJoinApplicationOfUser(partySeq, userSeq) == 0) {throw new MsgExceptionNull("2,존재하지 않는 신청서입니다.");}
    }

    public void exceptionParty(Long partySeq) {
        if (partySeq == null || partySeq == 0) {throw new NullReqValue();}
        if (mapper.checkPartySeq(partySeq) == 0) {throw new MsgExceptionNull("2,존재하지 않는 모임입니다.");}
    }
    public void exceptionUser(Long userSeq) {
        if (userSeq == null || userSeq == 0) {throw new NullReqValue();}
        if (mapper.checkUserSeq(userSeq) == 0) {throw new MsgExceptionNull("2,존재하지 않는 유저입니다.");}
    }
    // R1
    public void exceptionLeader(Long partySeq, Long userSeq) {
        exceptionParty(partySeq);
        exceptionUser(userSeq);
        if (mapper.checkPartyLeader(partySeq, userSeq) != 1){
            throw new MsgException("2,권한이 없는 유저입니다.");}
    }


    //-2.메세지 출력용(커스텀)
    @ExceptionHandler(MsgException.class)
    public ResultDto<String> handleMsgException(MsgException ex) {
        ex.printStackTrace();
        String msg = ex.getMessage();
        String[] parts = msg.split(",", 2);
        int code = Integer.parseInt(parts[0]);
        String resultMsg = parts[1];
        return ResultDto.resultDto(HttpStatus.BAD_REQUEST,code, resultMsg);
    }
    //-1.메세지 출력용(커스텀)
    @ExceptionHandler(MsgExceptionNull.class)
    public ResultDto<String> handleMsgException2(MsgExceptionNull ex) {
        ex.printStackTrace();
        String msg = ex.getMessage();
        String[] parts = msg.split(",", 2);
        int code = Integer.parseInt(parts[0]);
        String resultMsg = parts[1];
        return ResultDto.resultDto(HttpStatus.NOT_FOUND,code, resultMsg);
    }

    //0.메세지 출력용(커스텀)
    @ExceptionHandler(ReturnDto.class)
    public ResultDto<String> ReturnDto(ReturnDto ex) {
        ex.printStackTrace();
        String msg = ex.getMessage();
        String[] parts = msg.split(",", 2);
        int code = Integer.parseInt(parts[0]);
        String resultMsg = parts[1];
        return ResultDto.resultDto(HttpStatus.OK,code, resultMsg);
    }

    //1.Req 널(커스텀)
    @ExceptionHandler(NullReqValue.class)
    public ResultDto<String> handleNullReqValue(NullReqValue ex) {
        ex.printStackTrace();
        return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "정보를 제대로 입력해주세요.");
    }

    //2.런타임
    @ExceptionHandler(RuntimeException.class)
    public ResultDto<String> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "(join) 처리할 수 없는 요청입니다.");
    }
    //3.널포인트
    @ExceptionHandler(NullPointerException.class)
    public ResultDto<String> handleNullPointerException(NullPointerException ex) {
        ex.printStackTrace();
        return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "(join) 정보가 없습니다.");
    }
    //4.Exception
    @ExceptionHandler(Exception.class)
    public ResultDto<String> handleException(Exception ex) {
        ex.printStackTrace();
        return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "(join) 서버에러입니다.");
    }



}