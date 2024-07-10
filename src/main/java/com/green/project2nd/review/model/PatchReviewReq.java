package com.green.project2nd.review.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PatchReviewReq {
    @Schema(example = "1", description = "리뷰 PK")
    private long reviewSeq;
    @Schema(example = "제목", description = "리뷰 제목")
    private String reviewTitle;
    @Schema(example = "내용", description = "리뷰 내용")
    private String reviewContents;
    @Schema(example = "5", description = "별점")
    private int reviewRating;
}