package com.green.project2nd.party.exception;
import com.green.project2nd.common.CheckMapper;
import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.common.myexception.*;
import com.green.project2nd.party.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartFile;
@RequiredArgsConstructor
@Order(1)
@RestControllerAdvice(basePackages = "com.green.project2nd.party")
public class PartyExceptionHandler {
    private final CheckMapper mapper;

    public void exception(MultipartFile partyPic) {
        if (partyPic == null) {throw new NullPointerException();}
    }
    public void exception(Long partySeq) {
        if (partySeq == null || partySeq == 0) {throw new NullReqValue();}
        if (mapper.checkPartySeq(partySeq) == 0) {throw new NullValueInDatabase();}
    }
    public void exception(MultipartFile partyPic, PostPartyReq p) {
        exception(partyPic);
        exception(p.getPartySeq());
    }
    public void exception(MultipartFile partyPic, UpdatePartyReq p) {
        exception(partyPic);
        exception(p.getPartySeq());
    }
    public void exception(UpdatePartyReq p) {
        exception(p.getPartySeq());
    }





    //1.런타임
    @ExceptionHandler(RuntimeException.class)
    public ResultDto<String> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        return ResultDto.resultDto("RE", "PartyRuntimeException : 처리할 수 없는 요청입니다.");
    }
    //2.널포인트
    @ExceptionHandler(NullPointerException.class)
    public ResultDto<String> handleNullPointerException(NullPointerException ex) {
        ex.printStackTrace();
        return ResultDto.resultDto("NPE", "정보가 없습니다.");
    }
    //3.Req 널(ME)
    @ExceptionHandler(NullReqValue.class)
    public ResultDto<String> handleNullReqValue(NullReqValue ex) {
        ex.printStackTrace();
        return ResultDto.resultDto("NRV", "존재하지 않는 정보입니다.");
    }
    //4.Database 널(ME)
    @ExceptionHandler(NullValueInDatabase.class)
    public ResultDto<String> handleNullValueInDatabase(NullValueInDatabase ex) {
        ex.printStackTrace();
        return ResultDto.resultDto("NPV", "존재하지 않는 모임입니다.");
    }
    //5.데이터인터걸
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResultDto<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ex.printStackTrace();
        String errorMessage = "PartyDataIntegrityViolationException : 해당 부분을 입력해주세요.: " + ex.getRootCause().getMessage() +" (데이터 무결성 위반)";
        // 외래 키 제약 조건 위반 시 특정 메시지 제공
        if (ex.getMessage().contains("FOREIGN KEY")) {
            errorMessage = "PartyFOREIGN KEY ERROR: 존재하는 정보를 입력해주세요. (참조하는 키가 존재하지 않습니다.)";
        }
        return ResultDto.resultDto("HttpStatus.BAD_REQUEST", errorMessage);
    }

}
/*
    //1.런타임
    @ExceptionHandler(RuntimeException.class)
    public ResultDto<String> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        return ResultDto.resultDto("HttpStatus.INTERNAL_SERVER_ERROR", "RuntimeException : 처리할 수 없는 요청입니다.");
    }
    //2.널포인트
    @ExceptionHandler(NullPointerException.class)
    public ResultDto<String> handleNullPointerException(NullPointerException ex) {
        return ResultDto.resultDto("HttpStatus.BAD_REQUEST", "NullPointerException : 정보를 입력해주세요.");
    }
    //3.Req 널(ME)
    @ExceptionHandler(RuntimeException.class)
    public ResultDto<String> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        return ResultDto.resultDto("HttpStatus.INTERNAL_SERVER_ERROR", "RuntimeException : 처리할 수 없는 요청입니다.");
    }
    //4.Party 널(ME)
    @ExceptionHandler(RuntimeException.class)
    public ResultDto<String> handlePartyNullValue(RuntimeException ex) {
        ex.printStackTrace();
        return ResultDto.resultDto("HttpStatus.INTERNAL_SERVER_ERROR", "RuntimeException : 처리할 수 없는 요청입니다.");
    }

    //3.데이터인터걸
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResultDto<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ex.printStackTrace();
        String errorMessage = "DataIntegrityViolationException : 해당 부분을 입력해주세요.: " + ex.getRootCause().getMessage() +" (데이터 무결성 위반)";
        // 외래 키 제약 조건 위반 시 특정 메시지 제공
        if (ex.getMessage().contains("FOREIGN KEY")) {
            errorMessage = "FOREIGN KEY ERROR: 존재하는 정보를 입력해주세요. (참조하는 키가 존재하지 않습니다.)";
        }
        return ResultDto.resultDto("HttpStatus.BAD_REQUEST", errorMessage);
    }
 */