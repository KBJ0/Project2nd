package com.green.project2nd.review.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Getter
@Setter
public class GetReviewAllPageRes {
    private long totalElements;
    private long totalPages;
    private List<GetReviewAllRes> list;

    public GetReviewAllPageRes(long totalElements, int size, List<GetReviewAllRes> list) {
        this.totalElements = totalElements;
        if(size != 0) {
            this.totalPages = (this.totalElements + size - 1) / size;
        } else {
            this.totalPages = 1;
        }
        this.list = list;
    }
}
