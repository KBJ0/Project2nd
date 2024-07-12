package com.green.project2nd.review.model;

import com.green.project2nd.common.model.Paging;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetReviewUserReq extends Paging {
    private int search;
    private String searchData;
    private long userSeq;
    public GetReviewUserReq(Integer page, Integer size, int search, long userSeq, String searchData){
        super(page, size);
        this.search = search;
        this.userSeq = userSeq;
        this.searchData = searchData;
    }
}
