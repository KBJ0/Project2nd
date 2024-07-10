package com.green.project2nd.common;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto<T> {
    private HttpStatus statusCode;
    private String resultMsg;
    private T resultData;
}
