package com.green.project2nd.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BoardPostReq {
    private long boardPartySeq;
    private long boardMemberSeq;
    private String boardTitle;
    private String boardContents;
}
