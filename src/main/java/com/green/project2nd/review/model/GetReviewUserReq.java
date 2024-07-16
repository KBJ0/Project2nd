package com.green.project2nd.review.model;

import com.green.project2nd.common.model.Paging;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//public class GetReviewUserReq extends Paging {
public class GetReviewUserReq {
    private int search;
    private String searchData;
    private long userSeq;

    private Integer size;
    private Integer page;
    private int startIdx;

    public GetReviewUserReq(Integer page, Integer size, int search, long userSeq, String searchData){
        this.search = search;
        this.userSeq = userSeq;
        this.searchData = searchData;

        // 나중에 Paging 상속받아서 처리
        this.size = size == null ? 0 : size;
        this.page = page == null ? 0 : page;
        this.startIdx = this.page - 1 < 0 ? 0 : (this.page - 1) * this.size;
    }
}
