package com.green.project2nd.common;

import com.green.project2nd.common.model.ResultDto;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(2)
@RestControllerAdvice
public class GlobalExceptionHandler {
    //1.런타임
    @ExceptionHandler(RuntimeException.class)
    public ResultDto<String> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        return ResultDto.resultDto(HttpStatus.BAD_GATEWAY,2, "RuntimeException : 처리할 수 없는 요청입니다.");
    }

}