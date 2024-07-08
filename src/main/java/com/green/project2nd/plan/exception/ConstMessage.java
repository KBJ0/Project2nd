package com.green.project2nd.plan.exception;

public interface ConstMessage {
    String POST_SUCCESS_MESSAGE = "등록이 정상적으로 완료되었습니다.";
    String PATCH_SUCCESS_MESSAGE = "수정이 정상적으로 완료되었습니다.";
    String GET_SUCCESS_MESSAGE = "조회가 정상적으로 완료되었습니다.";
    String DELETE_SUCCESS_MESSAGE = "삭제가 정상적으로 완료되었습니다.";
    String NULL_ERROR_MESSAGE = "입력되지 않은 값이 있거나, 결과 값이 존재하지 않습니다.";
    String DENY_MESSAGE = "권한이 없습니다.";
    String RUNTIME_ERROR = "코드 에러입니다.";
}
