package com.green.project2nd.user;

import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.user.model.*;
import com.green.project2nd.user.userexception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.green.project2nd.user.userexception.ConstMessage.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final UserService service;

    @PostMapping("/sign_up")
    public ResultDto<Long> postSignUp(@RequestPart(value = "userPic") MultipartFile userPic, @RequestPart(value = "p") SignUpReq p) {
        log.info("userPic : {}", userPic);
        log.info("p : {}", p);

        try {
            long result = service.postSignUp(userPic, p);

            return ResultDto.<Long>builder()
                    .statusCode(HttpStatus.OK)
                    .resultMsg(SUCCESS_Message)
                    .resultData(result)
                    .build();
        } catch (EmailRegexException ee) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(EMAIL_REGEX_MESSAGE)
                    .resultData(-6L).build();   // 이메일 형식
        } catch (NicknameRegexException ne) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(NICKNAME_REGEX_MESSAGE)
                    .resultData(-5L).build();   // 닉네임 형식
        } catch (DuplicationException de) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(EMAIL_DUPLICATION_MESSAGE)
                    .resultData(-1L).build();   // 이메일 중복
        } catch (RuntimeException r) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(NICKNAME_DUPLICATION_MESSAGE)
                    .resultData(-2L).build();   // 닉네임 중복
        }

    }

    @PostMapping("/sign_in")
    public ResultDto<SignInRes> postSignIn(@RequestBody SignInReq p) {
        try {
            SignInRes result = service.postSignIn(p);

            return ResultDto.<SignInRes>builder()
                    .statusCode(HttpStatus.OK)
                    .resultMsg(SUCCESS_Message)
                    .resultData(result)
                    .build();
        } catch (NotFoundException une) {   // 존재x
            return ResultDto.<SignInRes>builder().statusCode(HttpStatus.NOT_FOUND).resultMsg(une.getMessage()).build();
        } catch (LoginException ule) {      // 아이디 or 비번 확인
            return ResultDto.<SignInRes>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(ule.getMessage()).build();
        }
    }

    @PatchMapping
    public ResultDto<Integer> patchPassword(@RequestBody PatchPasswordReq p) {
        int result = service.patchPassword(p);

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(SUCCESS_Message)
                .resultData(result)
                .build();
    }

    @DeleteMapping
    public ResultDto<Integer> deleteUser(@RequestParam("name = userSeq") Long userSeq) {
        int result = service.deleteUser(userSeq);

        // 서비스에서 던진 에러 처리하기

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(SUCCESS_Message)
                .resultData(result)
                .build();
    }

    @GetMapping("{userSeq}")
    public ResultDto<UserEntity> getDetailUserInfo(@PathVariable("userSeq") Long userSeq) {
        UserEntity result = service.getDetailUserInfo(userSeq);

        return ResultDto.<UserEntity>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(SUCCESS_Message)
                .resultData(result)
                .build();
    }

    @GetMapping("/duplicated")
    public ResultDto<String> duplicatedCheck(String str, int num) {
        log.info("str : {}", str);
        try {
            String result = service.duplicatedCheck(str, num);
            return ResultDto.<String>builder()
                    .statusCode(HttpStatus.OK)
                    .resultMsg(result == null ? "중복x" : "중복")
                    .build();
        } catch (Exception e) {
            return ResultDto.<String>builder()
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .resultMsg(ERROR_Message)
                    .build();
        }
    }

    @PatchMapping(value = "pic", consumes = "multipart/form-data")
    public ResultDto<String> updateUserPic(@ModelAttribute UpdateUserPicReq p) {

        try {
            String result = service.updateUserPic(p);
            return ResultDto.<String>builder()
                    .statusCode(HttpStatus.OK)
                    .resultMsg(SUCCESS_Message)
                    .resultData(result)
                    .build();
        } catch (Exception e) {
            return ResultDto.<String>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(ERROR_Message).build();
        }


    }
}
