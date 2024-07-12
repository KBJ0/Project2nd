package com.green.project2nd.board;

import com.green.project2nd.board.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper

public interface BoardMapper {
    int postBoard(BoardPostReq p);
    int postBoardPics(BoardPicPostDto p);
    int deleteBoardPics(long boardSeq);
    int deleteBoard(long boardSeq);
    int patchBoard(BoardPatchReq p);
    List<BoardGetRes> getBoard(long boardSeq);
}
