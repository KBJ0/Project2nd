package com.green.project2nd.review.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetReviewAllPageRes {
    private long totalElements;
    private long totalPages;
    private List<GetReviewAllRes> list;

    public GetReviewAllPageRes(List<GetReviewAllRes> list, long totalElements, Integer size) {
        this.totalElements = totalElements;
        this.totalPages = (this.totalElements + size - 1) / size;
        this.list = list;
    }
}
