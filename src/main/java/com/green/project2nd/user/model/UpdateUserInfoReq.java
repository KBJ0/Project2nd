package com.green.project2nd.user.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserInfoReq {
    private String nickName;
    private String userAddr;
    private String userFav;
    private String userPhone;
    private String userIntro;
}
