package com.green.project2nd.board.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class BoardPatchReq {
    private long boardSeq;
    private String boardTitle;
    private String boardContents;
    private List<String> nowFileNames;
    private List<String> deleteFileNames;
    private int result;
}
