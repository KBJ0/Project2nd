package com.green.project2nd.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultDto<T> {
//    private HttpStatus Code;
    private int code;
    private String resultMsg;
    private String pageData;
    private T resultData;
    public static <T> ResultDto<T> resultDto(int code, String resultMsg) {
        return ResultDto.<T>builder().code(code).resultMsg(resultMsg).build();
    }
    public static <T> ResultDto<T> resultDto(int code, String resultMsg, T resultData) {
        return ResultDto.<T>builder().code(code).resultMsg(resultMsg).resultData(resultData).build();
    }
    public static <T> ResultDto<T> resultDto(int code, String resultMsg, String pageData, T resultData) {
        return ResultDto.<T>builder().code(code).resultMsg(resultMsg).pageData(pageData).resultData(resultData).build();
    }

}