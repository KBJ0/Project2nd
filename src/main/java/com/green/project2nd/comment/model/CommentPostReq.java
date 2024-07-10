package com.green.project2nd.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CommentPostReq {
    //@JsonIgnore
    //private long comment_seq;

    private long commentBoardSeq;
    private long commentMemberSeq;
    private String commentContents;
}
