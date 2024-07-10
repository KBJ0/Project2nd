package com.green.project2nd.user;


import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.user.model.*;
import com.green.project2nd.user.userexception.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.green.project2nd.user.userexception.ConstMessage.*;



@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "User", description = "유저")
@RequestMapping("api/user")
public class UserController {
    private final UserService service;

    @PostMapping("/sign_up")
    @Operation(summary = "회원가입" , description =
            "<strong > 유저 회원가입 </strong> <p></p>" +
            "<p><strong> userEmail</strong> : 유저 이메일 (long) </p>" +
            "<p><strong> userPw</strong> : 유저 비밀번호 (String) </p>" +
            "<p><strong> userPwCheck</strong> : 유저 비밀번호 확인(String) </p>" +
            "<p><strong> userName</strong> : 유저 이름(String) </p>" +
            "<p><strong> userAddr</strong> : 유저 주소(String) </p>" +
            "<p><strong> userNickname</strong> : 유저 닉네임(String) </p>" +
            "<p><strong> userFav</strong> : 유저 관심 모임(long) </p>" +
            "<p><strong> userBirth</strong> : 유저 생년월일(Date) </p>" +
            "<p><strong> userGender</strong> : 유저 성별(int) </p>" +
            "<p><strong> userPhone</strong> : 유저 전화번호(String) </p>" +
            "<p><strong> userIntro</strong> : 유저 자기소개(String) </p>"
    )
    @ApiResponse(
            description =
                    "<p> ResponseCode 응답 코드 </p> " +
                            "<p>  유저 PK : 성공</p> " +
                            "<p>  0 : 실패 </p> " +
                            "<p> -1 : 닉네임 중복 </p> " +
                            "<p> -2 : 이메일 중복</p> " +
                            "<p> -3 : 비밀번호 확인 실패</p> " +
                            "<p> -4 : 비밀번호 검증 실패</p> " +
                            "<p> -5 : 닉네임 검증 실패</p> " +
                            "<p> -6 : 이메일 검증 실패</p> " +
                            "<p> -7 : 생년월일 검증 실패</p> " +
                            "<p> -8 : 알 수 없는 오류 발생 실패</p> "
    )
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
                    .resultData(-3L).build();   // 비밀번호 확인 불일치
        } catch (EmailRegexException ee) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(EMAIL_REGEX_MESSAGE)
                    .resultData(-6L).build();   // 이메일 형식
        } catch (NicknameRegexException ne) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(NICKNAME_REGEX_MESSAGE)
                    .resultData(-5L).build();   // 닉네임 형식
        } catch (DuplicationException de) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(EMAIL_DUPLICATION_MESSAGE)
                    .resultData(-2L).build();   // 이메일 중복
        } catch (BirthDateException be) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(BIRTHDATE_MESSAGE)
                    .resultData(-7L).build();   // 생년월일 형식
        } catch (RuntimeException r) {
            return ResultDto.<Long>builder().statusCode(HttpStatus.BAD_REQUEST).resultMsg(NICKNAME_DUPLICATION_MESSAGE)
                    .resultData(-1L).build();   // 닉네임 중복
        }
    }

    @PostMapping("/sign_in")
    @Operation(summary = "로그인" , description =
            "<strong > 유저 로그인 </strong> <p></p>" +
            "<p><strong> userEmail</strong> : 유저 이메일 (long) </p>" +
            "<p><strong> userPw</strong> : 유저 비밀번호 (String) </p>"
    )
    @ApiResponse(
            description =
                    "<p> ResponseCode 응답 코드 </p> " +
                            "<p>  유저 PK, 유저 닉네임, 유저 프로필 사진 : 성공</p> " +
                            "<p>  null (비회원가입 or 아이디 틀림 or 비밀번호 틀림) : 실패  </p> "
    )
    public ResultDto<SignInRes> postSignIn(HttpServletResponse res, @RequestBody SignInReq p) {
        try {
            SignInRes result = service.postSignIn(res, p);

            return ResultDto.<SignInRes>builder()
                    .statusCode(HttpStatus.OK)
                    .resultMsg(SUCCESS_Message)
                    .resultData(result)
                    .build();
        } catch (LoginException le) {      // 아이디 or 비번 확인 or 비회원가입
            return ResultDto.<SignInRes>builder().statusCode(HttpStatus.NOT_FOUND).resultMsg(le.getMessage()).build();
        }
    }
    @GetMapping("access-token")
    public ResultDto<Map<String, String>> getAccessToken(HttpServletRequest req) {
        Map<String, String> map = service.getAccessToken(req);

        return ResultDto.<Map<String, String>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("Access Token 발급")
                .resultData(map)
                .build();
    }

    @PatchMapping("/update/pw")
    @Operation(summary = "비밀번호 변경" , description = "비밀번호 변경")
    public ResultDto<Integer> patchPassword(@RequestBody UpdatePasswordReq p) {

        try {
            int result = service.patchPassword(p);
            return ResultDto.<Integer>builder()
                    .statusCode(HttpStatus.OK)
                    .resultMsg(SUCCESS_Message)
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
