package com.green.project2nd.user.email;


import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.user.model.FindPasswordReq;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "이메일 발송" , description = "이메일 발송(본인 인증용)")
    public String mailSend(@RequestBody @Valid EmailRequestDto emailDto) {
        log.info("이메일 인증 이메일 : {}", emailDto.getUserEmail());
        return mailService.joinEmail(emailDto.getUserEmail());
    }


    @PostMapping("/mailauthCheck")
    @Operation(summary = "이메일로 전송된 임시번호 확인" , description = "이메일로 전송된 임시번호 확인")
    public ResultDto<Integer> AuthCheck(@RequestBody @Valid EmailCheckDto p){   // 이메일이랑 임의 코드
        String Checked = mailService.CheckAuthNum(p.getUserEmail(),p.getAuthNum());
        log.info("Check : {}", Checked);
        log.info("p.getUserEmail() : {}", p.getUserEmail());
        log.info("p.getAuthNum() : {}", p.getAuthNum());
        switch (Checked) {
            case AUTH_CODE_EXPIRED -> {
                return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .resultMsg(AUTH_CODE_EXPIRED)
                .resultData(-1)
                .build();
            }
            case SUCCESS_Message -> {
                return ResultDto.<Integer>builder()
                        .statusCode(HttpStatus.OK)
                        .resultMsg(SUCCESS_Message)
                        .resultData(1)
                        .build();
            }
            case AUTH_CODE_INCORRECT -> {
                return ResultDto.<Integer>builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .resultMsg(AUTH_CODE_INCORRECT)
                        .resultData(0)
                        .build();
            }
            default -> throw new NullPointerException(ERROR_Message);
        }
    }

    @PatchMapping("/findpw")
    @Operation(summary = "이메일로 임시비밀번호 발급" , description = "이메일로 임시비밀번호 발급")
    public ResultDto<Integer> setPassword(@RequestBody FindPasswordReq p) {
        int result = mailService.setPassword(p);

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(result == 1? SUCCESS_Message : FAILURE_Message)
                .resultData(result)
                .build();
    }
}
