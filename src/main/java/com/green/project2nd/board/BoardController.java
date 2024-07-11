package com.green.project2nd.board;


import com.green.project2nd.board.model.BoardDeleteReq;
import com.green.project2nd.board.model.BoardPostReq;
import com.green.project2nd.board.model.BoardPostRes;
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

//    @PostMapping
//    @Operation(summary = "게시글 등록")
//    public ResultDto<BoardPostRes> postBoard(@RequestPart List<MultipartFile> pics, @RequestPart BoardPostReq p) {
//        BoardPostRes result = service.postBoard(pics, p);
//
//        return ResultDto.<BoardPostRes>builder()
//                .code(1)
//                .resultMsg(1.toString())
//                .resultData(result)
//                .build();
//    }
    @DeleteMapping
    @Operation(summary = "게시글 삭제")
    public ResultDto<Integer> deleteBoard(@RequestBody BoardDeleteReq p ) {
        int result = service.deleteBoard(p);

        return ResultDto.<Integer>builder()
                .code(1)
                .resultMsg(result == 1 ? "정상처리" : "실패")
                .resultData(result)
                .build();
    }
//  @GetMapping
  //  @Operation(summary = "게시글 조회")
 //   public ResultDto<List<BoardGetRes>> getBoard(@ModelAttribute@ParameterObject BoardGetReq p) {
  //      List<BoardGetRes> result = service.getBoard(p);
  //  }



}
