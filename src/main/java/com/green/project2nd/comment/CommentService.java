package com.green.project2nd.comment;

import com.green.project2nd.comment.comment_common.CommentGetPage;
import com.green.project2nd.comment.model.*;
import com.green.project2nd.common.GlobalConst;
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

    public CommentGetPage getBoardComment(CommentGetReq data) {
        List<CommentGetRes> list = mapper.getBoardComment(data);
        long totalElements = mapper.getTotalCount(data.getBoardSeq());

        return new CommentGetPage(list, GlobalConst.COMMENT_PAGING_SIZE, totalElements);
    }
}