package com.green.project2nd.user;

import com.green.project2nd.user.model.*;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int postSignUp(SignUpReq p);

    SimpleInfo getSimpleUserInfo(String userEmail);

    UserEntity getDetailUserInfo(Long userSeq);





    int patchPassword(PatchPasswordReq p);

    int deleteUser(Long userSeq);

    String checkEmail(String userEmail);
    String checkNickname(String userNickname);

    int updateUserPic(UpdateUserPicReq p);

}

