package com.green.project2nd.plan.model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PatchPlanReq {
    @Schema(example = "1", description = "모임 일정 PK 값")
    private long planSeq;

    @Schema(example = "24-07-23", description = "모임 일정 시작 날짜")
    private String planStartDt;

    @Schema(example = "12:00:00", description = "모임 일정 시작 시간")
    private String planStartTime;

    @Schema(example = "정기 모임", description = "일정 제목")
    private String planTitle;

    @Schema(example = "단체 회식", description = "일정 내용")
    private String planContents;
}
