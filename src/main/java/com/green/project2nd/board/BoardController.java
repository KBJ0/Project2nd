package com.green.project2nd.board;


import com.green.project2nd.board.model.*;
import com.green.project2nd.common.GlobalConst;
import com.green.project2nd.common.model.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/board")

public class BoardController {
    private final BoardService service;

    @PostMapping
    @Operation(summary = "게시글 등록")
    public ResultDto<BoardPostRes> postBoard(@RequestPart List<MultipartFile> pics, @RequestPart BoardPostReq p) {
        BoardPostRes result = service.postBoard(pics, p);

        return ResultDto.resultDto(HttpStatus.OK,1,HttpStatus.OK.toString(),result);
    }
    @DeleteMapping
    @Operation(summary = "게시글 삭제")
    public ResultDto<Integer> deleteBoard(@RequestBody BoardDeleteReq p ) {
        int result = service.deleteBoard(p);

        return ResultDto.resultDto(HttpStatus.OK,1,result == 1 ? "정상처리" : "실패",result);

    }

    @PatchMapping
    @Operation(summary = "게시글 수정")
    public ResultDto<BoardPatchReq> patchBoard(@RequestPart List<MultipartFile> newPics, @RequestPart BoardPatchReq p) {
        int result = service.patchBoard(newPics, p);
        p.setResult(result);
        return ResultDto.<BoardPatchReq>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(result == 1 ? "정상처리" : "실패")
                .resultData(p)
                .build();

    }
    @GetMapping
    @Operation(summary = "게시글 조회")
    public ResultDto<BoardGetPage> getBoards(@RequestParam(name = "page", defaultValue = "0") int page) {
        BoardGetReq data = new BoardGetReq(0, page, GlobalConst.PAGING_SIZE);
        BoardGetPage list = service.getBoardList(data);
        return ResultDto.<BoardGetPage>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("정상처리 되었습니다")
                .resultData(list)
                .build();
    }
    @GetMapping("/{boardSeq}")
    @Operation(summary = "게시글 상세 조회")
    public ResultDto<BoardGetRes> getBoard(@PathVariable long boardSeq) {
        BoardGetRes board = service.getBoard(boardSeq);
        return ResultDto.<BoardGetRes>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("정상처리 되었습니다")
                .resultData(board)
                .build();
    }
}
