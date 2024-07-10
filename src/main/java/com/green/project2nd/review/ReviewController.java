package com.green.project2nd.review;

import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.review.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
@Tag(name = "Review (리뷰 관리)", description = "리뷰 CRUD")
public class ReviewController {
    private final ReviewService service;

    @PostMapping
    @Operation(summary = "리뷰 등록")
    public ResultDto<PostReviewRes> postReview(@RequestPart(value = "pics", required = false) List<MultipartFile> pics, @RequestPart PostReviewReq p) {

        if(p.getReviewTitle() == null) {    //제목 예외처리
            return ResultDto.resultDto("2", "제목에 내용이 있어야 합니다.");
        }
        if(p.getReviewContents() == null) { // 내용 예외처리
            return ResultDto.resultDto("2", "내용이 있어야 합니다.");
        }
        if(p.getReviewRating() <= 0 || p.getReviewRating() > 5) {   // 별점 예외처리
            return ResultDto.resultDto("2", "별점은 0~5점이어야 합니다.");
        }

        PostReviewRes result = service.postReview(pics, p);

        if(result == null) {
            return ResultDto.resultDto("2", "리뷰 등록 실패 (리턴값 : NULL)");
        }

        return ResultDto.resultDto("1","리뷰 등록 완료" ,result);
    }

    @DeleteMapping
    @Operation(summary = "리뷰 삭제")
    public ResultDto<Long> deleteReview(@RequestParam(name = "reviewSeq") long reviewSeq) {
        if(reviewSeq == 0) {        //userSeq 예외처리
            ResultDto.resultDto("2", "리뷰 PK가 없습니다.");
        }

        long result = service.deleteReview(reviewSeq);

        if (result == 0) {
            return ResultDto.resultDto("2", "삭제 실패 (적용 행 : 0)");
        }

        return ResultDto.resultDto("1","삭제 완료", result);
    }

    @GetMapping
    @Operation(summary = "리뷰 검색")
    public ResultDto<List<GetReviewAllRes>> getReviewAll(@Nullable @RequestParam(name = "search") Integer search
                                                        ,@Nullable @RequestParam(name = "page") Integer page
                                                        ,@Nullable @RequestParam(name = "size") Integer size) {
        if(search == null) {
            search = 0;
        }
        GetReviewAllReq p = new GetReviewAllReq(page, size, search);
        List<GetReviewAllRes> result = service.getReviewAll(p);

        if(result == null) {
            ResultDto.resultDto("2", "리뷰 조회 실패(검색 결과값 : 0)");
        }
        return ResultDto.resultDto("1", "리뷰 조회 완료", result);
    }

    @GetMapping("/user")
    @Operation(summary = "내가 적은 리뷰 검색")
    public ResultDto<List<GetReviewUserRes>> getReviewUser(@Nullable @RequestParam(name = "search") Integer search
            , @Nullable @RequestParam(name = "page") Integer page
            , @Nullable @RequestParam(name = "size") Integer size
            , @RequestParam(name = "userSeq") long userSeq) {
        if(userSeq == 0) {  //userSeq 예외처리
            return ResultDto.resultDto("2", "유저 PK가 없습니다.");
        }

        if(search == null) {
            search = 0;
        }

        GetReviewUserReq p = new GetReviewUserReq(page, size, search, userSeq);

        List<GetReviewUserRes> result = service.getReviewUser(p);

        if(result == null) {
            ResultDto.resultDto("2", "리뷰 조회 실패(검색 결과값 : 0)");
        }
        return ResultDto.resultDto("1", "리뷰 조회 완료", result);
    }

    @PatchMapping
    @Operation(summary = "리뷰 수정")
    public ResultDto<List<String>> patchReview(@RequestPart(value = "pics", required = false) List<MultipartFile> pics
                                                         , @RequestPart PatchReviewReq p) {
        if(p.getReviewSeq() == 0) {         //리뷰PK 예외처리
            return ResultDto.resultDto("2", "리뷰가 존재하지 않거나 PK 값이 전달되지 않았습니다.");
        }
        if(p.getReviewTitle() == null) {    //제목 예외처리
            return ResultDto.resultDto("2", "제목에 내용이 있어야 합니다.");
        }
        if(p.getReviewContents() == null) { // 내용 예외처리
            return ResultDto.resultDto("2", "내용이 있어야 합니다.");
        }
        if(p.getReviewRating() <= 0 || p.getReviewRating() > 5) {   // 별점 예외처리
            return ResultDto.resultDto("2", "별점은 0~5점이어야 합니다.");
        }

        List<String> result = service.patchReview(pics, p);

        return ResultDto.resultDto("1", "리뷰 수정 완료", result);
    }
}
