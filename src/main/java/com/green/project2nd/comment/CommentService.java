package com.green.project2nd.comment;

import com.green.project2nd.comment.model.CommentDeleteReq;
import com.green.project2nd.comment.model.CommentGetRes;
import com.green.project2nd.comment.model.CommentPatchReq;
import com.green.project2nd.comment.model.CommentPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper mapper;


    public long postBoardComment(CommentPostReq p) {
        long res = mapper.postBoardComment(p);
        if (res == 0) {
            return 2;
        }
        return res;
    }

    public int deleteBoardComment(CommentDeleteReq p) {
        int res = mapper.deleteBoardComment(p);
        if (res == 0) {
            return 2;
        }
        return res;
    }

    public int patchBoardComment(CommentPatchReq p) {
        int res = mapper.patchBoardComment(p);
        if (res == 0) {
            return 2;
        }
        return res;
    }

    public List<CommentGetRes> getBoardComment(long boardSeq) {
        return mapper.getBoardComment(boardSeq);
    }
}