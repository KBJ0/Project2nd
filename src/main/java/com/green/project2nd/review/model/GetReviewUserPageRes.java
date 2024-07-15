package com.green.project2nd.review.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Getter
@Setter
public class GetReviewUserPageRes {
    private List<GetReviewUserRes> list;
    private long totalElements;
    private long totalPages;
    private int size;

    public GetReviewUserPageRes(List<GetReviewUserRes> list, long totalElements, int size) {
        this.list = list;
        this.totalElements = totalElements;
        this.totalPages = (this.totalElements + size - 1) / size;
        this.size = size;
    }
}