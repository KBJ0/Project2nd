package com.green.project2nd.budget.exception;

import com.green.project2nd.common.CheckMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartFile;

import static com.green.project2nd.budget.exception.ConstMessage.*;

@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.green.project2nd.budget")
public class BudgetExceptionHandler {
    private final CheckMapper mapper;

    public void exception(MultipartFile budgetPic) {
        if(budgetPic == null) {
            throw new NullPointerException(NULL_ERROR_MESSAGE);
        }
    }

    public void exception(Long budgetPartySeq) {
        if(mapper.checkBudgetPartySeq(budgetPartySeq) == null) {
            throw new NullPointerException(NULL_ERROR_MESSAGE);
        }
    }

    public void exception2(Long budgetSeq){
        if(mapper.checkBudgetSeq(budgetSeq) == null ) {
            throw new NullPointerException(NULL_ERROR_MESSAGE);
        }
    }

}
