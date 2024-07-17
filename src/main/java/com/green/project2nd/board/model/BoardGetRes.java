package com.green.project2nd.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BoardGetRes {
    private long boardPartySeq;
    private long boardMemberSeq;
    private String boardTitle;
    private String boardContents;
    private long boardHit;
    private String inputDt;
    private String updateDt;


}
