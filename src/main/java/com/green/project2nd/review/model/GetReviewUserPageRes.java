package com.green.project2nd.review.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetReviewUserPageRes {
    private long totalElements;
    private long totalPages;
    private List<GetReviewUserRes> list;

    public GetReviewUserPageRes(List<GetReviewUserRes> list, long totalElements, Integer size) {
        this.totalElements = totalElements;
        this.totalPages = (this.totalElements + size - 1) / size;
        this.list = list;
    }
}
