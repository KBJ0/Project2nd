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
    @Operation(summary = "게시글 등록" ,description =
    "<strong> 모임 커뮤니티 게시글 등록 (Postman으로 테스트)</strong><p></P>" +
    "<p><strong> boardPartySeq   </strong> : 게시글PK (long) </p>" +
    "<p><strong> boardMemberSeq  </strong> : 유저PK (long) </p>"   +
    "<p><strong> boardTitle </strong> : 게시글 제목 (string) </p>" +
    "<p><strong> boardContents </strong> : 게시글 내용 (string </p>)"
    )
    public ResultDto<BoardPostRes> postBoard(@RequestPart List<MultipartFile> pics, @RequestPart BoardPostReq p) {
        BoardPostRes result = service.postBoard(pics, p);

        return ResultDto.<BoardPostRes>builder()
                .status(HttpStatus.OK)
                .code(1)
                .resultMsg("정상처리 되었습니다")
                .resultData(result)
                .build();
    }
    @DeleteMapping
    @Operation(summary = "게시글 삭제" ,description =
            "<strong> 커뮤니티 게시글 삭제   </strong><p></p>" +
                    "<p><strong> boardSeq  </strong> : 게시글 PK (long) </p>" +
                    "<p><strong> boardMemberSeq </strong> : 게시글 유저 PK (long) </p>"
    )
    public ResultDto<Integer> deleteBoard(@RequestBody BoardDeleteReq p ) {
        int result = service.deleteBoard(p);

        return ResultDto.<Integer>builder()
                .status(HttpStatus.OK)
                .code(1)
                .resultMsg("정상처리 되었습니다")
                .resultData(result)
                .build();
    }

    @PatchMapping
    @Operation(summary = "게시글 수정" ,description =
            "<strong> 커뮤니티 게시글 수정    </strong><p></p>" +
                    "<p><strong> boardSeq  </strong> : 게시글 PK (long) </p>" +
                    "<p><strong> boardMemberSeq </strong> : 게시글 유저 PK (long) </p>" +
                    "<p><strong> boardTitle   </strong> : 게시글 제목 (strong) </p>" +
                    "<p><strong> nowFileNames </strong> : 수정시 존재할 사진 (String) </p>" +
                    "<p><strong> deleteFileNames </strong> : 수정시 삭제할 사진 (string) </p>"
    )
    public ResultDto<BoardPatchReq> patchBoard(@RequestParam List<MultipartFile> newPics, @RequestBody BoardPatchReq p) {
        boolean result = service.boardPatch(newPics, p);
        return ResultDto.<BoardPatchReq>builder()
                .status(HttpStatus.OK)
                .code(1)
                .resultMsg("정상처리 되었습니다")
                .resultData(p)
                .build();
    }
    @GetMapping
    @Operation(summary = "게시글 조회" ,description =
            "<strong> 커뮤니티 게시글 조회    </strong><p></p>" +
                    "<p><strong> commentBoardSeq  </strong> : 게시글PK (long) </p>"
    )
    public ResultDto<BoardGetPage> getBoards(@RequestParam(name = "page", defaultValue = "0") int page) {
        BoardGetReq data = new BoardGetReq(0, page, GlobalConst.PAGING_SIZE);
        BoardGetPage list = service.getBoardList(data);
        return ResultDto.<BoardGetPage>builder()
                .status(HttpStatus.OK)
                .code(1)
                .resultMsg("정상처리 되었습니다")
                .resultData(list)
                .build();
    }
    @GetMapping("/{boardSeq}")
    @Operation(summary = "게시글 상세 조회", description =
            "<strong> 커뮤니티 댓글 등록    </strong><p></p>" +
                    "<p><strong> commentBoardSeq  </strong> : 게시글PK (long) </p>" +
                    "<p><strong> commentMemberSeq </strong> : 댓글 유저PK (long) </p>" +
                    "<p><strong> commentContents  </strong> : 댓글 내용 (strong) </p>"
    )
    public ResultDto<BoardGetRes> getBoard(@PathVariable long boardSeq) {
        BoardGetRes board = service.getBoard(boardSeq);
        return ResultDto.<BoardGetRes>builder()
                .status(HttpStatus.OK)
                .code(1)
                .resultMsg("정상처리 되었습니다")
                .resultData(board)
                .build();
    }
}
