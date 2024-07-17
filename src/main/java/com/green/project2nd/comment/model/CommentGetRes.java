package com.green.project2nd.comment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CommentGetRes {
    private long commentSeq;
    private long commentBoardSeq;
    private long commentMemberSeq;
    private String commentContents;
    private String inputDt;
    private String updateDt;
}
