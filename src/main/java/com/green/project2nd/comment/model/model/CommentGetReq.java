package com.green.project2nd.comment.model.model;

import com.green.project2nd.common.model.Paging;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CommentGetReq extends Paging {
    private long boardSeq;

    public CommentGetReq(long boardSeq, Integer page, Integer size) {
        super(page, size);
        this.boardSeq = boardSeq;
    }
}


