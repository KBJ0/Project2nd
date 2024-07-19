package com.green.project2nd.planjoin;

import com.green.project2nd.common.CheckMapper;
import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.planjoin.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.green.project2nd.planjoin.exception.ConstMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanJoinService {
    private final PlanJoinMapper mapper;
    private final CheckMapper checkMapper;

    public ResultDto<Integer> postPlanJoin(TogglePlanJoinReq p) {
        if (checkMapper.checkPlanJoin(p) != null) {
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST, 2, ERROR_MESSAGE_1);
        } else {
            mapper.postPlanJoin(p);
            return ResultDto.resultDto(HttpStatus.OK, 1, POST_SUCCESS_MESSAGE);
        }
    }

    public ResultDto<Integer> deletePlanJoin(TogglePlanJoinReq p) {
        if (checkMapper.checkPlanJoin(p) == null) {
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST, 2, NULL_ERROR_MESSAGE);
        } else {
            mapper.deletePlanJoin(p);
            return ResultDto.resultDto(HttpStatus.OK, 1, DELETE_SUCCESS_MESSAGE);

        }
    }
}
