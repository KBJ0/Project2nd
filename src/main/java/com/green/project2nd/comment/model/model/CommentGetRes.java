package com.green.project2nd.comment.model.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class CommentGetRes {
    private long commentSeq;
    private long commentBoardSeq;
    private long commentMemberSeq;
    private String commentContents;
    private LocalDateTime inputDt;
    private LocalDateTime updateDt;
}
