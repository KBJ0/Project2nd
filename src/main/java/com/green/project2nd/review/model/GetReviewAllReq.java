package com.green.project2nd.review.model;

import com.green.project2nd.common.model.Paging;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetReviewAllReq extends Paging {
    private int search;
    public GetReviewAllReq(Integer page, Integer size, int search){
        super(page, size);
        this.search = search;
    }
}
