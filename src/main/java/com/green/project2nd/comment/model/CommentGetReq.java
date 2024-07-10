package com.green.project2nd.comment.model;

import com.green.project2nd.common.model.Paging;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CommentGetReq extends Paging {
    private long commentBoardSeq;
    private int page;

    public CommentGetReq(long commentBoardSeq, Integer page, Integer size) {
        super(page,size);
        this.commentBoardSeq = commentBoardSeq;
    }
}
