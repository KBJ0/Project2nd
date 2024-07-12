package com.green.project2nd.board.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode

public class BoardPicPostDto {
    private long boardSeq;
    private List<String> fileNames = new ArrayList();
}
