package com.green.project2nd.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.project2nd.common.GlobalConst;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Paging {
    @Schema(description = "페이지 번호")
    private Integer page;
    @Schema(description = "보여줄 내용 수")
    private Integer size;

    public Paging(Integer page, Integer size) {
        this.page = page == null ? GlobalConst.PAGE_NUM : page;
        this.size = size == null ? GlobalConst.SIZE_NUM : size;
        this.startIdx = this.page - 1 < 0 ? 0 : (this.page - 1) * this.size;
    }
    @JsonIgnore
    private Integer startIdx;
}
