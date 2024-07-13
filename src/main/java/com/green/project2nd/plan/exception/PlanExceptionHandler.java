package com.green.project2nd.plan.exception;

import com.green.project2nd.common.CheckMapper;
import com.green.project2nd.plan.model.*;
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

    public void exceptionAll(PostPlanReq p){
        if(p.getPlanPartySeq() == null || p.getPlanStartDt() == null || p.getPlanStartTime() == null
                || p.getPlanTitle() == null || p.getPlanContents() ==null) {
            throw new NullPointerException(NULL_ERROR_MESSAGE);
        }
    }
}
