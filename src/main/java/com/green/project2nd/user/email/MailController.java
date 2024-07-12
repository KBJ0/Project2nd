package com.green.project2nd.user.email;


import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.user.model.FindPasswordReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.green.project2nd.user.userexception.ConstMessage.*;



@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "email", description = "email 작업")
public class MailController {
    private final MailSendService mailService;

    @PostMapping("/mailSend")
    @Operation(summary = "이메일 발송" , description =
    "<strong > 이메일 발송(본인 인증용) </strong> <p></p>" +
            "<p><strong> userEmail</strong> : 유저 이메일 (String) </p>"
    )
    @ApiResponse(
            description =
                    "<p> ResponseCode 응답 코드 </p> " +
                            "<p>  1 : 성공  </p> " +
                            "<p>  2 : 실패  </p> " +
                            "<p>  3 : 에러 "
    )
    public ResultDto<String> mailSend(@RequestBody @Valid EmailRequestDto emailDto) {
        log.info("이메일 인증 이메일 : {}", emailDto.getUserEmail());
        try {
            String str = mailService.joinEmail(emailDto.getUserEmail());
            if(str.equals(UNREGISTERED_EMAIL_MESSAGE)) {
                return ResultDto.<String>builder().status(HttpStatus.BAD_REQUEST).code(FAILURE)
                        .resultMsg(str).build();
            }
            if(str.equals(EMAIL_ALREADY_VERIFIED_MESSAGE)) {
                return ResultDto.<String>builder().status(HttpStatus.CONFLICT).code(FAILURE)
                        .resultMsg(str).build();
            }
            return ResultDto.<String>builder().status(HttpStatus.OK).code(SUCCESS)
                    .resultMsg(str).build();

        } catch (RuntimeException e) {
            return ResultDto.<String>builder().status(HttpStatus.INTERNAL_SERVER_ERROR).code(ERROR)
                    .resultMsg(TRY_AGAIN_MESSAGE).build();
        }
    }


    @PostMapping("/mailauthCheck")
    @Operation(summary = "이메일로 전송된 임시번호 확인" , description =
    "<strong > 이메일로 전송된 임시번호 확인 </strong> <p></p>" +
            "<p><strong> userEmail</strong> : 유저 이메일 (String) </p>" +
            "<p><strong> authNum</strong> : 인증번호 (String) </p>"
            )
    @ApiResponse(
            description =
                    "<p> ResponseCode 응답 코드 </p> " +
                            "<p>  1 : 성공  </p> " +
                            "<p>  2 : 실패  </p> " +
                            "<p>  3 : 에러 "
    )
    public ResultDto<Integer> AuthCheck(@RequestBody @Valid EmailCheckDto p){   // 이메일이랑 임의 코드
        String Checked = mailService.CheckAuthNum(p.getUserEmail(),p.getAuthNum());
        log.info("Check : {}", Checked);
        log.info("p.getUserEmail() : {}", p.getUserEmail());
        log.info("p.getAuthNum() : {}", p.getAuthNum());
        switch (Checked) {
            case SUCCESS_Message -> {
                return ResultDto.<Integer>builder()
                        .status(HttpStatus.OK).code(SUCCESS)
                        .resultMsg(SUCCESS_Message).build();
            }
            case AUTH_CODE_INCORRECT -> {
                return ResultDto.<Integer>builder()
                        .status(HttpStatus.BAD_REQUEST).code(FAILURE)
                        .resultMsg(AUTH_CODE_INCORRECT).build();
            }
            case AUTH_CODE_EXPIRED -> {
                return ResultDto.<Integer>builder()
                        .status(HttpStatus.CONFLICT).code(FAILURE)
                        .resultMsg(AUTH_CODE_EXPIRED).build();
            }
            case TRY_AGAIN_MESSAGE -> {
                return ResultDto.<Integer>builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR).code(ERROR)
                        .resultMsg(TRY_AGAIN_MESSAGE).build();
            }
            default -> {
                return ResultDto.<Integer>builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR).code(ERROR)
                        .resultMsg(ADMIN_CONTACT_MESSAGE).build();
            }
        }
    }

    @PatchMapping("/findpw")
    @Operation(summary = "이메일로 임시비밀번호 발급" , description =
    "<strong > 이메일로 임시비밀번호 발급 </strong> <p></p>" +
            "<p><strong> userEmail</strong> : 유저 이메일 (String) </p>"
            )
    @ApiResponse(
            description =
                    "<p> ResponseCode 응답 코드 </p> " +
                            "<p>  1 : 성공  </p> " +
                            "<p>  2 : 실패  </p> " +
                            "<p>  3 : 에러 "
    )
    public ResultDto<Integer> setPassword(@RequestBody FindPasswordReq p) {

        try {
            int result = mailService.setPassword(p);
            if(result == 0) {
                return ResultDto.<Integer>builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .code(FAILURE)
                        .resultMsg(UNREGISTERED_EMAIL_MESSAGE)
                        .resultData(result)
                        .build();
            }
            return ResultDto.<Integer>builder()
                    .status(HttpStatus.OK)
                    .code(SUCCESS)
                    .resultMsg(SUCCESS_Message)
                    .resultData(result)
                    .build();
        } catch (RuntimeException e) {
            return ResultDto.<Integer>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .code(ERROR)
                    .resultMsg(TRY_AGAIN_MESSAGE)
                    .build();
        }
    }
}