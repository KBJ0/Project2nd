package com.green.project2nd.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordReq {
    private String userEmail;

    @JsonIgnore
    private String setPw;
}
