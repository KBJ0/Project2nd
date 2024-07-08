package com.green.project2nd.planjoin.exception;

import com.green.project2nd.common.CheckMapper;
import com.green.project2nd.planjoin.model.TogglePlanJoinReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.green.project2nd.planjoin")
public class PlanJoinExceptionHandler {
    private final CheckMapper mapper;

    public void exception(TogglePlanJoinReq p) {
        if(mapper.checkPlanJoin(p) == 1) {
            throw new NullPointerException();
        }
    }

    public void exception2(TogglePlanJoinReq p){
        if(mapper.checkPlanJoin(p) == 0) {
            throw new NullPointerException();
        }
    }
}
