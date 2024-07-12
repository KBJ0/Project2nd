package com.green.project2nd.comment;


import com.green.project2nd.comment.model.CommentDeleteReq;
import com.green.project2nd.comment.model.CommentGetRes;
import com.green.project2nd.comment.model.CommentPatchReq;
import com.green.project2nd.comment.model.CommentPostReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CommentMapper {
    long postBoardComment(CommentPostReq p);
    int deleteBoardComment(CommentDeleteReq p);
    int patchBoardComment(CommentPatchReq p);
    List<CommentGetRes> getBoardComment(long boardSeq);
}
