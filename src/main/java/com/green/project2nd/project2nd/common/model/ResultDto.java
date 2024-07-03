package com.green.project2nd.project2nd.common.model;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto<T> {
    private HttpStatus statusCode;
    private String code;
    private String resultMsg;
    private T resultData;
    public static <T> ResultDto<T> resultDto(String code, String resultMsg) {
        return ResultDto.<T>builder().code(code).resultMsg(resultMsg).build();
    }

    public static <T> ResultDto<T> resultDto(String code, String resultMsg, T resultData) {
        return ResultDto.<T>builder().code(code).resultMsg(resultMsg).resultData(resultData).build();
    }

}
