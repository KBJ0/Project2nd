package com.green.project2nd.review.model;

import com.green.project2nd.common.GlobalConst;
import com.green.project2nd.common.model.Paging;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
//public class GetReviewAllReq extends Paging {
public class GetReviewAllReq{
    private Integer search;
    private String searchData;

    private Integer size;
    private Integer page;
    private int startIdx;

    public GetReviewAllReq(Integer page, Integer size, Integer search, String searchData) {
//        super(page, size);
        this.search = search;
        this.searchData = searchData;

        // 나중에 Paging 상속받아서 처리
        this.size = size == null ? 0 : size;
        this.page = page == null ? 0 : page;
        this.startIdx = this.page - 1 < 0 ? 0 : (this.page - 1) * this.size;
    }
}