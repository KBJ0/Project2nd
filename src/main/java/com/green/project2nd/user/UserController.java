package com.green.project2nd.user;

import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.user.model.*;
import com.green.project2nd.user.userexception.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.green.project2nd.user.userexception.ConstUser.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "User", description = "유저")
@RequestMapping("api/user")
public class UserController {
    private final UserService service;

    @PostMapping("/sign_up")
    @Operation(summary = "회원가입" , description = "유저 회원가입")
    public ResultDto<Long> postSignUp(@RequestPart(value = "userPic") MultipartFile userPic, @RequestPart(value = "p") SignUpReq p) {

        try {
            long result = service.postSignUp(userPic, p);

            return ResultDto.<Long>builder()
                    .statusCode(HttpStatus.OK)
                    .resultMsg(SUCCESS_Message)
                    .resultData(result)
                    .build();
        } catch (PwCheckException pe) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(PASSWORD_CHECK_MESSAGE)
                    .resultData(-8L).build();   // 비밀번호 확인 불일치
        } catch (EmailRegexException ee) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(EMAIL_REGEX_MESSAGE)
                    .resultData(-6L).build();   // 이메일 형식
        } catch (NicknameRegexException ne) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(NICKNAME_REGEX_MESSAGE)
                    .resultData(-5L).build();   // 닉네임 형식
        } catch (DuplicationException de) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(EMAIL_DUPLICATION_MESSAGE)
                    .resultData(-1L).build();   // 이메일 중복
        } catch (BirthDateException be) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(BIRTHDATE_MESSAGE)
                    .resultData(-8L).build();   // 생년월일 형식
        } catch (RuntimeException r) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(NICKNAME_DUPLICATION_MESSAGE)
                    .resultData(-2L).build();   // 닉네임 중복
        }
    }

    @PostMapping("/sign_in")
    @Operation(summary = "로그인" , description = "로그인")
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

    @PatchMapping("/update/pw")
    @Operation(summary = "비밀번호 변경" , description = "비밀번호 변경")
    public ResultDto<Integer> patchPassword(@RequestBody UpdatePasswordReq p) {

        try {
            int result = service.patchPassword(p);
            return ResultDto.<Integer>builder()
                    .statusCode(HttpStatus.OK)
                    .resultMsg(result == 1 ? SUCCESS_Message : FAILURE_Message)
                    .resultData(result)
                    .build();
        } catch (IdCheckException re) {
            return ResultDto.<Integer>builder()
                    .statusCode(HttpStatus.NOT_FOUND)
                    .resultMsg(FAILURE_Message)
                    .resultData(0)
                    .build();
        } catch (PwCheckException pe) {
            return ResultDto.<Integer>builder()
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .resultMsg(ERROR_Message)
                    .resultData(-1)
                    .build();
        }
    }

    @PatchMapping("{userSeq}")
    @Operation(summary = "유저 탈퇴" , description = "유저 탈퇴")
    public ResultDto<Integer> deleteUser(@PathVariable("userSeq") Long userSeq) {
        int result = service.deleteUser(userSeq);

        // 서비스에서 던진 에러 처리하기

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(result == 1 ? SUCCESS_Message : FAILURE_Message)
                .resultData(result)
                .build();
    }

    @GetMapping("{userSeq}")
    @Operation(summary = "마이페이지" , description = "마이페이지")
    public ResultDto<UserEntity> getDetailUserInfo(@PathVariable("userSeq") Long userSeq) {
        UserEntity result = service.getDetailUserInfo(userSeq);

        return ResultDto.<UserEntity>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(SUCCESS_Message)
                .resultData(result)
                .build();
    }

    @GetMapping("/duplicated")
    @Operation(summary = "이메일, 닉네임 중복체크" , description = "이메일, 닉네임 중복체크 (1 : 이메일, 2 : 닉네임)")
    public ResultDto<Integer> duplicatedCheck(String str, int num) {
        log.info("str : {}", str);
        try {
            int result = service.duplicatedCheck(str, num);
            return ResultDto.<Integer>builder()
                    .statusCode(HttpStatus.OK)
                    .resultMsg(result == 1 ? "중복O" : "중복X")
                    .resultData(result)
                    .build();
        } catch (Exception e) {
            return ResultDto.<Integer>builder()
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .resultMsg(ERROR_Message)
                    .build();
        }
    }

    @PatchMapping(value = "pic", consumes = "multipart/form-data")
    @Operation(summary = "유저 프로필 변경" , description = "유저 프로필 변경")
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

    @PatchMapping("/update/myInfo")
    @Operation(summary = "유저 정보 수정" , description = "유저 정보 수정")
    public ResultDto<Integer> updateUserInfo(@RequestBody UpdateUserInfoReq p) {
        int result = service.updateUserInfo(p);

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(result == 1 ? SUCCESS_Message : FAILURE_Message)
                .resultData(result)
                .build();
    }

    @PostMapping("/findid")
    @Operation(summary = "아이디 찾기" , description = "아이디 찾기")
    public ResultDto<String> findUserId(@RequestBody FindUserReq p) {
        String result = service.findUserId(p);

        return ResultDto.<String>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(result == null ? FAILURE_Message : SUCCESS_Message)
                .resultData(result)
                .build();
    }


}
