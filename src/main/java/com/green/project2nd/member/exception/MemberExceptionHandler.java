package com.green.project2nd.member.exception;

import com.green.project2nd.common.CheckMapper;
import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.common.myexception.MsgException;
import com.green.project2nd.common.myexception.NullReqValue;
import com.green.project2nd.member.model.UpdateMemberReq;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RequiredArgsConstructor
@Order(1)
@RestControllerAdvice(basePackages = "com.green.project2nd.member")
public class MemberExceptionHandler {
    private final CheckMapper mapper;



    // R2 U2 D1
    public void exception(Long memberPartySeq, Long memberUserSeq) {
        exceptionParty(memberPartySeq);
        exceptionUser(memberUserSeq);

        if (mapper.checkMemberForPartySeqAndUserSeq(memberPartySeq,memberUserSeq) == 0){throw new MsgException("2,존재하지 않는 멤버입니다.");}
    }
    // U1
    public void exception(Long memberPartySeq, UpdateMemberReq p) {
        exception(memberPartySeq, p.getMemberUserSeq());
    }
    // U3
    public void exceptionLeader(Long memberPartySeq, Long memberLeaderUserSeq) {
        exception(memberPartySeq,memberLeaderUserSeq);
        if (mapper.checkPartyLeader(memberPartySeq, memberLeaderUserSeq) != 1){
            throw new MsgException("2,권한이 없는 유저입니다.");
        }
    }
    // R1
    public void exceptionParty(Long memberPartySeq) {
        if (memberPartySeq == null || memberPartySeq == 0) {throw new NullReqValue();}
        if (mapper.checkPartySeq(memberPartySeq) == 0) {throw new MsgException("2,존재하지 않는 모임입니다.");}
    }

    public void exceptionUser(Long memberUserSeq) {
        if (memberUserSeq == null || memberUserSeq == 0) {throw new NullReqValue();}
        if (mapper.checkUserSeq(memberUserSeq) == 0) {throw new MsgException("2,존재하지 않는 유저입니다.");}
    }

    // C1
//    public void exception(PostMemberReq p) {
//        exception(p.getMemberPartySeq(), p.getMemberUserSeq());
//    }


    //0.메세지 출력용(커스텀)
    @ExceptionHandler(MsgException.class)
    public ResponseEntity<ResultDto<String>> handleMsgException(MsgException ex) {
        ex.printStackTrace();
        String msg = ex.getMessage();
        String[] parts = msg.split(",", 2);
        int code = Integer.parseInt(parts[0]);
        String resultMsg = parts[1];
        ResultDto<String> result = ResultDto.resultDto(code, resultMsg);
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //1.Req 널(커스텀)
    @ExceptionHandler(NullReqValue.class)
    public ResultDto<String> handleNullReqValue(NullReqValue ex) {
        ex.printStackTrace();
        return ResultDto.resultDto(2, "정보를 제대로 입력해주세요.");
    }

    //2.런타임
    @ExceptionHandler(RuntimeException.class)
    public ResultDto<String> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        return ResultDto.resultDto(2, "(member) 처리할 수 없는 요청입니다.");
    }
    //3.널포인트
    @ExceptionHandler(NullPointerException.class)
    public ResultDto<String> handleNullPointerException(NullPointerException ex) {
        ex.printStackTrace();
        return ResultDto.resultDto(2, "(member) 정보가 없습니다.");
    }
    //4.Exception
    @ExceptionHandler(Exception.class)
    public ResultDto<String> handleException(Exception ex) {
        ex.printStackTrace();
        return ResultDto.resultDto(2, "(member) 서버에러입니다.");
    }


}