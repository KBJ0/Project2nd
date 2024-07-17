package com.green.project2nd.board.model.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BoardDeleteReq {
    private long boardSeq;
    private long boardMemberSeq;
}
