package com.green.project2nd.user.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UpdateUserPicReq {

    private long userSeq;
    private MultipartFile pic;

    @JsonIgnore
    private String picName;
}
