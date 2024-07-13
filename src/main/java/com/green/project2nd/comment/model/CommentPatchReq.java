package com.green.project2nd.comment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CommentPatchReq {
    private long commentSeq;
    private long commentMemberSeq;
    private String commentContents;

}
