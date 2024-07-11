package com.green.project2nd.partywish;


import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.partywish.model.PartyWishToggleReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.green.project2nd.user.userexception.ConstMessage.SUCCESS;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/party/wish")
public class PartyWishController {
    private final PartyWishService service;

    @GetMapping
    @Operation(summary = "관심모임 찜" , description =
            "<strong > 관심 모임 찜 </strong> <p></p>" +
            "<p><strong> wishUserSeq</strong> : 유저 PK (long) </p>" +
            "<p><strong> wishPartySeq</strong> : 모임 PK (long) </p>"
    )
    @ApiResponse(
            description =
                    "<p> ResponseCode 응답 코드 </p> " +
                            "<p>  관심모임 추가 : 1 </p> " +
                            "<p>  관심모임 삭제 : 0 </p> "
    )
    public ResultDto<Integer> togglePartyWish(@ParameterObject @ModelAttribute PartyWishToggleReq p) {
        int result = service.togglePartyWish(p);

        return ResultDto.<Integer>builder()
                .code(SUCCESS)
                .resultMsg(result == 0 ? "관심모임 삭제" : "관심모임 추가")
                .resultData(result)
                .build();
    }
}
