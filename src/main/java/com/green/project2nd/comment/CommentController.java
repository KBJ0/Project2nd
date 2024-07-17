package com.green.project2nd.comment;


import com.green.project2nd.comment.comment_common.CommentGetPage;
import com.green.project2nd.comment.model.*;
import com.green.project2nd.common.model.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.green.project2nd.common.GlobalConst.COMMENT_PAGING_SIZE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/board/comment")
public class CommentController {
    private final CommentService service;

    @PostMapping
    @Operation(summary = "댓글 등록")
    public ResultDto<Long> postComment(@RequestBody CommentPostReq p) {
        long result = service.postBoardComment(p);
        return ResultDto.<Long>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(result == 1 ? "정상처리" : "실패")
                .resultData(result)
                .build();
    }

    @DeleteMapping
    @Operation(summary = "댓글 삭제")
    public ResultDto<Integer> deleteComment(@RequestBody CommentDeleteReq p) {
        int result = service.deleteBoardComment(p);
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(result == 1 ? "정상처리" : "실패")
                .resultData(result)
                .build();
    }

    @PatchMapping
    @Operation(summary = "댓글 수정")
    public ResultDto<Integer> patchComment(@ModelAttribute @ParameterObject CommentPatchReq p) {
        int result = service.patchBoardComment(p);
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(result == 1 ? "정상처리" : "실패")
                .resultData(result)
                .build();
    }

    @GetMapping
    @Operation(summary = "댓글 조회")
    public ResultDto<CommentGetPage> getComment(@RequestParam(name = "boardSeq") long boardSeq, Integer page) {
        CommentGetReq data = new CommentGetReq(boardSeq, page, COMMENT_PAGING_SIZE);
        CommentGetPage list = service.getBoardComment(data);
        // List<CommentGetRes> list = service.getBoardComment(boardSeq);
        return ResultDto.<CommentGetPage>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("정상처리 되었습니다")
                .resultData(list)
                .build();
    }

}
