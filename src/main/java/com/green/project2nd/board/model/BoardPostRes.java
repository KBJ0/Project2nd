package com.green.project2nd.board.model;

import lombok.*;

import java.util.List;

@Data
@Builder
public class BoardPostRes {
    private long boardSeq;
    private List<String> pics;
}
