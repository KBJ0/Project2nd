package com.green.project2nd.user;


import com.green.project2nd.common.model.CustomFileUtils;
import com.green.project2nd.user.datacheck.Const;
import com.green.project2nd.user.model.*;
import com.green.project2nd.user.userexception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.green.project2nd.user.userexception.ConstUser.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final CustomFileUtils customFileUtils;


    @Transactional
    public Long postSignUp(MultipartFile userPic, SignUpReq p) {
        if(!p.getUserPw().equals(p.getUserPwCheck())) {
            throw new PwCheckException(PASSWORD_CHECK_MESSAGE);
        }
        if(!Const.isValidEmail(p.getUserEmail())) {
            throw new EmailRegexException(EMAIL_REGEX_MESSAGE);
        }
        if(!Const.isValidNickname(p.getUserNickname())) {
            throw new NicknameRegexException(NICKNAME_REGEX_MESSAGE);
        }
        if(mapper.duplicatedCheckEmail(p.getUserEmail()) == 1) {
            throw new DuplicationException(EMAIL_DUPLICATION_MESSAGE);
        }
        if(mapper.duplicatedCheckNickname(p.getUserNickname()) == 1) {
            throw new RuntimeException(NICKNAME_DUPLICATION_MESSAGE);
        }
        if(Const.isValidBirthDate(String.valueOf(p.getUserBirth()))) {
            throw new BirthDateException(BIRTHDATE_MESSAGE);
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

    public SignInRes postSignIn(SignInReq p) {
        SimpleInfo simpleInfo = mapper.getSimpleUserInfo(p.getUserEmail());

        if(simpleInfo == null) {
            throw new NotFoundException(ConstUser.NOT_FOUND_MESSAGE);
        } else if(!(p.getUserEmail().equals(simpleInfo.getUserEmail())) || !(BCrypt.checkpw(p.getUserPw(), simpleInfo.getUserPw()))) {
            throw new LoginException(ConstUser.LOGIN_MESSAGE);
        }

        return SignInRes.builder()
                .userNickname(simpleInfo.getUserNickname())
                .userPic(simpleInfo.getUserPic())
                .userSeq(simpleInfo.getUserSeq())
                .build();
    }

    public int patchPassword(UpdatePasswordReq p) {
        SimpleInfo simpleInfo = mapper.getSimpleUserInfo(p.getUserEmail());

        if(simpleInfo == null) {
            throw new IdCheckException(ID_CHECK_MESSAGE);
        } else if (!(BCrypt.checkpw(p.getUpw(), simpleInfo.getUserPw()))) {
            throw new PwCheckException(PASSWORD_CHECK_MESSAGE); // 예외처리 세분화 하기
        }
        if(!p.getNewPw().equals(p.getUserPwCheck())) {
            throw new PwCheckException(PASSWORD_CHECK_MESSAGE);
        }

        String newPassword = BCrypt.hashpw(p.getNewPw(), BCrypt.gensalt());
        p.setNewPw(newPassword);
        p.setUserSeq(simpleInfo.getUserSeq());
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
