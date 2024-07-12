package com.green.project2nd.review;

import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.review.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
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
    @Operation(summary = "리뷰 등록", description =
    "<strong> 유저 리뷰 등록 (PostMan으로 테스트)</strong><p></p>" +
    "<p><strong> reviewPlanSeq      </strong> : 일정 PK (long) </p>" +
    "<p><strong> reviewPlmemberSeq  </strong> : 일정 참가자 PK (long) </p>" +
    "<p><strong> reviewTitle        </strong> : 리뷰 제목 (String) </p>" +
    "<p><strong> reviewContents     </strong> : 내용 (String) </p>" +
    "<p><strong> reviewRating       </strong> : 별점 (int) </p>"
    )
    @ApiResponse(
            description =
                    "<p> ResponseCode 응답 코드 </p>" +
                            "<p> 1 : 성공 (유저PK, 사진 리턴)</p>" +
                            "<p> 2 : 실패, ResultMsg</p>"
    )
    public ResultDto<PostReviewRes> postReview(@RequestPart(value = "pics", required = false) List<MultipartFile> pics, @RequestPart PostReviewReq p) {

        if(p.getReviewTitle() == null) {    // 제목 예외처리
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "제목을 입력해주세요.");
        }
        if(p.getReviewContents() == null) { // 내용 예외처리
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "내용을 입력해주세요.");
        }
        if(p.getReviewRating() < 1 || p.getReviewRating() > 5) {   // 별점 예외처리
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "별점은 1~5점이어야 합니다.");
        }
        try {
            PostReviewRes result = service.postReview(pics, p);

            if(result == null) {
                return ResultDto.resultDto(HttpStatus.INTERNAL_SERVER_ERROR,2, "리뷰 등록 실패 (리턴값 : NULL)");
            }
            return ResultDto.resultDto(HttpStatus.OK, 1,"리뷰 등록 완료" ,result);
        } catch (Exception e) {
            return ResultDto.resultDto(HttpStatus.INTERNAL_SERVER_ERROR,2, "파일 업로드 중 오류가 발생했습니다.");
        }
    }

    @DeleteMapping
    @Operation(summary = "리뷰 삭제", description =
            "<strong> 본인이 적은 리뷰 삭제 </strong><p></p>" +
                    "<p><strong> reviewSeq </strong> : 리뷰 PK (long) </p>"
    )
    @ApiResponse(
            description =
                    "<p> ResponseCode 응답 코드 </p>" +
                            "<p> 1 : 성공 (DB에서 영향을 받은 행 갯수 리턴)</p>" +
                            "<p> 2 : 실패, ResultMsg</p>"
    )
    public ResultDto<Integer> deleteReview(@RequestParam(name = "reviewSeq") long reviewSeq) {
        if(reviewSeq == 0) {        //userSeq 예외처리
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "리뷰 PK가 전달되지 않았습니다.");
        }

        int result = service.deleteReview(reviewSeq);

        if (result == 0) {
            return ResultDto.resultDto(HttpStatus.INTERNAL_SERVER_ERROR,2, "삭제 실패 (적용 행 : 0)");
        }

        return ResultDto.resultDto(HttpStatus.OK,1,"삭제 완료(result = 영향받은 행 수)", result);
    }

    @GetMapping
    @Operation(summary = "리뷰 조회", description =
            "<strong> 전체 리뷰 조회 </strong><p></p>" +
                    "<p><strong> search </strong> : 검색어 구분값 (int) 1:전체 2:모임명 3:모임장명 4: 작성자명 (디폴트값 : 1)</p>" +
                    "<p><strong> searchData </strong> : 검색어 (String) (NULL 허용)" +
                    "<p><strong> page   </strong> : 페이지 번호 (Integer) (NULL 허용, 디폴트값 : 1)</p>" +
                    "<p><strong> size   </strong> : 페이지별 게시글 수 (Integer) (NULL 허용, 디폴트값 : 10)</p>"
    )
    @ApiResponse(
            description =
                    "<p> ResponseCode 응답 코드 </p>" +
                            "<p> 1 : 성공 (사진,리뷰내용 List 형식으로 리턴)</p>" +
                            "<p> 2 : 실패, ResultMsg</p>"
    )
    public ResultDto<GetReviewAllPageRes> getReviewAll(
            @Nullable @RequestParam(name = "page") Integer page
            , @Nullable @RequestParam(name = "size") Integer size
            , @RequestParam(name = "search", defaultValue = "1") Integer search
            , @Nullable @RequestParam(name = "searchData") String searchData
    ) {

        if(searchData == null) {
            searchData = "";
        }

        if(page < 0 || size < 0) {
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST, 2, "사이즈나 페이지는 자연수로 입력해주세요.");
        }

        GetReviewAllReq p = new GetReviewAllReq(page, size, search, searchData);
        GetReviewAllPageRes result = service.getReviewAll(p);

        if(result == null) {
            return ResultDto.resultDto(HttpStatus.INTERNAL_SERVER_ERROR,2, "리뷰 조회 실패(검색 결과값 : 0)");
        }
        return ResultDto.resultDto(HttpStatus.OK,1, "리뷰 조회 완료", result);
    }

    @GetMapping("/user")
    @Operation(summary = "내가 적은 리뷰 검색", description =
            "<strong> 마이페이지 내가 적은 리뷰 </strong><p></p>" +
                    "<p><strong> userSeq   </strong> : 유저 PK (Integer) </p>" +
                    "<p><strong> search </strong> : 검색어 구분값 (int) 1:전체 2:모임명 3:모임장명 4: 작성자명</p>" +
                    "<p><strong> searchData </strong> : 검색어 (String) (NULL 허용)" +
                    "<p><strong> page   </strong> : 페이지 번호 (Integer) (NULL 허용, 디폴트값 : 1)</p>" +
                    "<p><strong> size   </strong> : 페이지별 게시글 수 (Integer) (NULL 허용, 디폴트값 : 10)</p>"
    )
    @ApiResponse(
            description =
                    "<p> ResponseCode 응답 코드 </p>" +
                            "<p> 1 : 성공 (사진,리뷰내용 List 형식으로 리턴)</p>" +
                            "<p> 2 : 실패, ResultMsg</p>"
    )
    public ResultDto<GetReviewUserPageRes> getReviewUser(@RequestParam(name = "search", defaultValue = "1") Integer search
            , @Nullable @RequestParam(name = "searchData") String searchData
            , @Nullable @RequestParam(name = "page", defaultValue = "1") Integer page
            , @Nullable @RequestParam(name = "size", defaultValue = "10") Integer size
            , @RequestParam(name = "userSeq") long userSeq) {
        if(userSeq == 0) {  //userSeq 예외처리
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "유저 PK가 전달되지 않았습니다.");
        }

        if(searchData == null) {
            searchData = "";
        }

        if(page < 0 || size < 0) {
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST, 2, "사이즈나 페이지는 자연수로 입력해주세요.");
        }

        GetReviewUserReq p = new GetReviewUserReq(page, size, search, userSeq, searchData);

        GetReviewUserPageRes result = service.getReviewUser(p);

        if(result == null) {
            return ResultDto.resultDto(HttpStatus.INTERNAL_SERVER_ERROR,2, "리뷰 조회 실패(검색 결과값 : 0)");
        }
        return ResultDto.resultDto(HttpStatus.OK,1, "리뷰 조회 완료", result);
    }

    @PatchMapping
    @Operation(summary = "리뷰 수정", description =
            "<strong> 유저 리뷰 수정 (PostMan으로 테스트)</strong><p></p>" +
                    "<p><strong> reviewSeq          </strong> : 리뷰 PK (long) </p>" +
                    "<p><strong> reviewTitle        </strong> : 리뷰 제목 (String) </p>" +
                    "<p><strong> reviewContents     </strong> : 내용 (String) </p>" +
                    "<p><strong> reviewRating       </strong> : 별점 (int) </p>"
    )
    @ApiResponse(
            description =
                    "<p> ResponseCode 응답 코드 </p>" +
                            "<p> 1 : 성공 (사진 리턴)</p>" +
                            "<p> 2 : 실패, ResultMsg</p>"
    )
    public ResultDto<List<String>> patchReview(@RequestPart(value = "pics", required = false) List<MultipartFile> pics
                                                         , @RequestPart PatchReviewReq p) {
        if(p.getReviewSeq() == 0) {         //리뷰PK 예외처리
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "리뷰가 존재하지 않거나 리뷰 PK 값이 전달되지 않았습니다.");
        }
        if(p.getReviewTitle() == null) {    //제목 예외처리
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "제목을 입력해주세요.");
        }
        if(p.getReviewContents() == null) { // 내용 예외처리
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "내용을 입력해주세요.");
        }
        if(p.getReviewRating() <= 0 || p.getReviewRating() > 5) {   // 별점 예외처리
            return ResultDto.resultDto(HttpStatus.BAD_REQUEST,2, "별점은 0~5점이어야 합니다.");
        }

        try {
            List<String> result = service.patchReview(pics, p);

            return ResultDto.resultDto(HttpStatus.OK,1, "리뷰 수정 완료", result);
        } catch (Exception e){
            return ResultDto.resultDto(HttpStatus.INTERNAL_SERVER_ERROR,2, "파일 업로드 중 오류가 발생했습니다.");
        }
    }
}
