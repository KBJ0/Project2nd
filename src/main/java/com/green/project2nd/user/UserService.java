package com.green.project2nd.user;


import com.green.project2nd.common.model.AppProperties;
import com.green.project2nd.common.model.CookieUtils;
import com.green.project2nd.security.AuthenticationFacade;
import com.green.project2nd.security.MyUser;
import com.green.project2nd.security.MyUserDetails;
import com.green.project2nd.security.jwt.JwtTokenProviderV2;
import com.green.project2nd.user.datacheck.Const;
import com.green.project2nd.user.model.*;
import com.green.project2nd.user.userexception.*;
import com.green.project2nd.common.model.CustomFileUtils;
import com.green.project2nd.user.model.SignInReq;
import com.green.project2nd.user.model.SignInRes;
import com.green.project2nd.user.model.SignUpReq;
import com.green.project2nd.user.model.UserEntity;

import com.green.project2nd.user.userexception.DuplicationException;
import com.green.project2nd.user.userexception.LoginException;
import com.green.project2nd.user.userexception.NotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.green.project2nd.user.userexception.ConstMessage.*;



@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final JwtTokenProviderV2 jwtTokenProvider;
    private final CookieUtils cookieUtils;
    private final AuthenticationFacade authenticationFacade;
    private final AppProperties appProperties;


    @Transactional
    public Long postSignUp(MultipartFile userPic, SignUpReq p) {
        if(!p.getUserPw().equals(p.getUserPwCheck())) {
            throw new PwCheckException(PASSWORD_CHECK_MESSAGE);
        }
        if(!com.green.project2nd.user.datacheck.Const.isValidEmail(p.getUserEmail())) {
            throw new EmailRegexException(EMAIL_REGEX_MESSAGE);
        }
        if(!com.green.project2nd.user.datacheck.Const.isValidNickname(p.getUserNickname())) {
            throw new NicknameRegexException(NICKNAME_REGEX_MESSAGE);
        }
        if(mapper.duplicatedCheckEmail(p.getUserEmail()) == 1) {
            throw new DuplicationException(EMAIL_DUPLICATION_MESSAGE);
        }
        if(mapper.duplicatedCheckNickname(p.getUserNickname()) == 1) {
            throw new RuntimeException(NICKNAME_DUPLICATION_MESSAGE);
        }
        log.info("p.getUserBirth() : {}", p.getUserBirth());
        log.info("String.valueOf(p.getUserBirth()) : {}", String.valueOf(p.getUserBirth()));
        if(Const.isValidDate(p.getUserBirth())) {
            throw new BirthDateException(BIRTHDATE_MESSAGE);
        } else {
            Const.convertToDate(p.getUserBirth());
        }


        // 생년월일 체크

        String saveFileName = customFileUtils.makeRandomFileName(userPic);
        p.setUserPic(saveFileName);
        String hashPw = BCrypt.hashpw(p.getUserPw(), BCrypt.gensalt());
        p.setUserPw(hashPw);

        int result = mapper.postSignUp(p);

        if(userPic == null) {
            return null;    // 예외처리 하기
        }
        try {
            String path = String.format("user/%d", p.getUserSeq());
            customFileUtils.makeFolders(path);
            String target = String.format("%s/%s", path, saveFileName);
            customFileUtils.transferTo(userPic, target);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("파일 저장 오류"); //구체적 예외처리 하기
        }
        return p.getUserSeq();
    }

    public SignInRes postSignIn(HttpServletResponse res, SignInReq p) {
        SimpleInfo user = mapper.getSimpleUserInfo(p.getUserEmail());

        if(user == null || !(p.getUserEmail().equals(user.getUserEmail()))
                || !(BCrypt.checkpw(p.getUserPw(), user.getUserPw()))) {
            throw new LoginException(ConstMessage.LOGIN_MESSAGE);
        }
        MyUser myUser = MyUser.builder()
                .userId(user.getUserSeq())
                .role("ROLE_USER")
                .build();

        String accessToken = jwtTokenProvider.generateAccessToken(myUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(myUser);

        // refreshToken은 보안 쿠키를 이용해서 처리 (프론트가 따로 작업을 하지 않아도 아래 cookie 값은 항상 넘어온다.)
        // 쿠키에 담는 부분
        int refreshTokenMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge();
        cookieUtils.deleteCookie(res, "refresh-token");
        log.info("appProperties.getJwt().getRefreshTokenCookieName() : {}", appProperties.getJwt().getRefreshTokenCookieName());
        cookieUtils.setCookie(res, appProperties.getJwt().getRefreshTokenCookieName(), refreshToken, refreshTokenMaxAge);


        return SignInRes.builder()
                .userNickname(user.getUserNickname())
                .userPic(user.getUserPic())
                .userSeq(user.getUserSeq())
                .accessToken(accessToken)
                .build();
    }
    public Map getAccessToken(HttpServletRequest req) {
        Cookie cookie = cookieUtils.getCookie(req, appProperties.getJwt().getRefreshTokenCookieName());
        if(cookie == null) { // refresh-token 값이 쿠키에 존재 여부
            throw new RuntimeException();
        }

        String refreshToken = cookie.getValue();
        if(!jwtTokenProvider.isValidateToken(refreshToken)) { //refresh-token 만료시간 체크
            throw new RuntimeException();
        }

        UserDetails auth = jwtTokenProvider.getUserDetailsFromToken(refreshToken);
        MyUser myUser = ((MyUserDetails)auth).getMyUser();

        String accessToken = jwtTokenProvider.generateAccessToken(myUser);

        Map map = new HashMap();
        map.put("accessToken", accessToken);
        return map;
    }

    public int patchPassword(UpdatePasswordReq p) {
        SimpleInfo user = mapper.getSimpleUserInfo(p.getUserEmail());

        if(user == null) {
            throw new IdCheckException(ID_CHECK_MESSAGE);
        } else if (!(BCrypt.checkpw(p.getUpw(), user.getUserPw()))) {
            throw new PwCheckException(PASSWORD_CHECK_MESSAGE); // 예외처리 세분화 하기
        }
        if(!p.getNewPw().equals(p.getUserPwCheck())) {
            throw new PwCheckException(PASSWORD_CHECK_MESSAGE);
        }

        String newPassword = BCrypt.hashpw(p.getNewPw(), BCrypt.gensalt());
        p.setNewPw(newPassword);
        p.setUserSeq(user.getUserSeq());
        return mapper.patchPassword(p);

    }

    public int deleteUser(Long userSeq) {
        try {
            String midPath = String.format("user/%d", userSeq);
            String delAbsoluteFolderPath = String.format("%s%s", customFileUtils.uploadPath, midPath);
            customFileUtils.deleteFolder(delAbsoluteFolderPath);

        } catch (Exception e) {
            throw new RuntimeException(ERROR_Message);    // 구체적인 에러로 수정
        }
        return mapper.deleteUser(userSeq);
    }

    public UserEntity getDetailUserInfo(Long userSeq) {
        return mapper.getDetailUserInfo(userSeq);

    }

    public int duplicatedCheck(String str, int num) {   // 1 : 이메일, 2 : 닉네임
        switch (num) {
            case 1 -> num = mapper.duplicatedCheckEmail(str);
            case 2 -> num = mapper.duplicatedCheckNickname(str);
            default -> throw new RuntimeException(ERROR_Message);
        }
        return num;
    }

    public String updateUserPic(UpdateUserPicReq p) {
        p.setUserSeq(authenticationFacade.getLoginUserId());

        String fileName = customFileUtils.makeRandomFileName(p.getPic());
        p.setPicName(fileName);
        mapper.updateUserPic(p);

        try {
            String Path = String.format("user/%d", p.getUserSeq());
            String delAbsoluteFolderPath = String.format("%s%s", customFileUtils.uploadPath, Path);
            customFileUtils.deleteFolder(delAbsoluteFolderPath);


            customFileUtils.makeFolders(Path);
            String filePath = String.format("%s/%s", Path, fileName);
            customFileUtils.transferTo(p.getPic(), filePath);

        } catch (Exception e) {
            throw new RuntimeException(ERROR_Message);  // 에러 처리하기
        }
        return fileName;
    }

    public int updateUserInfo(UpdateUserInfoReq p) {
        return mapper.updateUserInfo(p);
    }

    public String findUserId(FindUserReq p) {
        return mapper.findUserId(p);
    }

}
