package com.green.project2nd.board.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder

public class BoardPicPostDto {
    private long boardSeq;
    private List<String> fileNames = new ArrayList<>();
}
