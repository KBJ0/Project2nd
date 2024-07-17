package com.green.project2nd.board.model.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BoardPostReq {
    @JsonIgnore
    private long boardSeq;

    private long boardPartySeq;
    private long boardMemberSeq;
    private String boardTitle;
    private String boardContents;
}