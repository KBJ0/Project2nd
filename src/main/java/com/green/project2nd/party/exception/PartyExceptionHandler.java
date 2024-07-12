package com.green.project2nd.party.exception;

import com.green.project2nd.common.CheckMapper;
import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.common.myexception.MsgException;
import com.green.project2nd.common.myexception.MsgExceptionNull;
import com.green.project2nd.common.myexception.NullReqValue;
import com.green.project2nd.common.myexception.ReturnDto;
import com.green.project2nd.party.model.PostPartyReq;
import com.green.project2nd.party.model.UpdatePartyReq;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Order(1)
@RestControllerAdvice(basePackages = "com.green.project2nd.party")
public class PartyExceptionHandler {
    private final CheckMapper mapper;

    // C
    public void exception(@Nullable MultipartFile partyPic, PostPartyReq p) {
        exception(partyPic);
        exception(p.getPartyName());
        exceptionUser(p.getUserSeq());
    }
    // U
    public void exception(@Nullable MultipartFile partyPic, UpdatePartyReq p) {
        exception(partyPic);
        exception(p.getPartySeq());
        exceptionLeader(p.getPartySeq(), p.getUserSeq());
    }
    // U2, D, U3
    public void exception(Long partySeq, Long userSeq) {
        exception(partySeq);
        exceptionUser(userSeq);
        exceptionLeader(partySeq, userSeq);
    }
    // R2
    public void exception(Long partySeq) {
        if (partySeq == null || partySeq == 0) {throw new NullReqValue();}
        if (mapper.checkPartySeq(partySeq) == 0) {
            throw new MsgExceptionNull("2,존재하지 않는 모임입니다.");}
    }
    // R3, R4
    public void exceptionUser(Long userSeq) {
        if (userSeq == null || userSeq == 0) {throw new NullReqValue();}
        if (mapper.checkUserSeq(userSeq) == 0) {throw new MsgExceptionNull("2,존재하지 않는 유저입니다.");}
    }
    public void exceptionLeader(Long partySeq, Long userSeq) {
        if (mapper.checkPartyLeader(partySeq, userSeq) != 1){
            throw new MsgException("2,권한이 없는 유저입니다.");
        }
    }
    public void exception(@Nullable MultipartFile partyPic) {
        if (partyPic == null || partyPic.isEmpty()) {throw new MsgExceptionNull("2,사진은 필수입니다.");}
    }
    public void exception(String partyName) {
        if (partyName == null) {throw new NullReqValue();}
        if (mapper.checkPartyName(partyName) != 0){
            throw new MsgException("2,이미 존재하는 모임명칭입니다.");
        }
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
        return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "(party) 처리할 수 없는 요청입니다.");
    }
    //3.널포인트
    @ExceptionHandler(NullPointerException.class)
    public ResultDto<String> handleNullPointerException(NullPointerException ex) {
        ex.printStackTrace();
        return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "(party) 정보가 없습니다.");
    }
    //4.Exception
    @ExceptionHandler(Exception.class)
    public ResultDto<String> handleException(Exception ex) {
        ex.printStackTrace();
        return ResultDto.resultDto(HttpStatus.INTERNAL_SERVER_ERROR,2, "(party) 서버에러입니다.");
    }
}