package com.green.project2nd.plan.exception;

import com.green.project2nd.common.CheckMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.green.project2nd.plan.exception.ConstMessage.NULL_ERROR_MESSAGE;

@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.green.project2nd.plan")
public class PlanExceptionHandler {
    private final CheckMapper mapper;

    public void exception(long planSeq) {
        if(mapper.checkPlanSeq(planSeq) == 0) {
            throw new NullPointerException(NULL_ERROR_MESSAGE);
        }
    }

    public void exception2(long planPartySeq){
        if(mapper.checkPlanPartySeq(planPartySeq) == 0) {
            throw new NullPointerException(NULL_ERROR_MESSAGE);
        }
    }
}
