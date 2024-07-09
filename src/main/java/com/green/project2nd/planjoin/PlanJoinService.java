package com.green.project2nd.planjoin;
import com.green.project2nd.planjoin.exception.PlanJoinExceptionHandler;
import com.green.project2nd.planjoin.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class PlanJoinService {
    private final PlanJoinMapper mapper;
    private final PlanJoinExceptionHandler check;

    public int postPlanJoin(TogglePlanJoinReq p){
        check.exception(p);
        return mapper.postPlanJoin(p);
    };

    public int deletePlanJoin(TogglePlanJoinReq p){
        check.exception2(p);
        return mapper.deletePlanJoin(p);
    };
}
