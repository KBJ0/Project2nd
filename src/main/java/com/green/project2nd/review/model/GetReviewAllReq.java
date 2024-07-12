package com.green.project2nd.review.model;

import com.green.project2nd.common.model.Paging;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetReviewAllReq extends Paging {
    private Integer search;
    private String searchData;

    public GetReviewAllReq(Integer page, Integer size, Integer search, String searchData) {
        super(page, size);
        this.search = search;
        this.searchData = searchData;
    }
}