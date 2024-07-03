package com.green.project2nd.project2nd.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchPasswordReq {
    private String userEmail;
    private String upw;
    private String newPw;

    @JsonIgnore
    private Long userSeq;
}
