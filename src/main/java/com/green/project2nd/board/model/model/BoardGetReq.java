package com.green.project2nd.board.model.model;

import com.green.project2nd.common.model.Paging;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BoardGetReq extends Paging {
    private long boardSeq;

    public BoardGetReq(long boardSeq, Integer page, Integer size) {
        super(page, size);
        this.boardSeq = boardSeq;
    }

}
