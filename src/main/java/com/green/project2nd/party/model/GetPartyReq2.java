package com.green.project2nd.party.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetPartyReq2 {
    @Schema(example = "1", description = "유저 PK")
    private long userSeq;
    private int page;
    @JsonIgnore
    private int size;

    public GetPartyReq2(Integer page, Integer size) {
        this.page = page == null ? 1 : page;
        this.size = size == null ? 9 : size;
        this.startIdx = (this.page - 1) * this.size;
    }
    @JsonIgnore
    private int startIdx;

}
