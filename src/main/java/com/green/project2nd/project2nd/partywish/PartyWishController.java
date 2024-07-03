package com.green.project2nd.project2nd.partywish;

import com.green.project2nd.common.ResultDto;
import com.green.project2nd.partywish.PartyWishService;
import com.green.project2nd.partywish.model.PartyWishToggleReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/party/wish")
public class PartyWishController {
    private final PartyWishService service;

    @GetMapping
    public ResultDto<Integer> togglePartyWish(@ParameterObject @ModelAttribute PartyWishToggleReq p) {
        int result = service.togglePartyWish(p);

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(result == 0 ? "관심모임 삭제" : "관심모임 추가")
                .resultData(result)
                .build();
    }
}
