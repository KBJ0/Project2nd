package com.green.project2nd.party.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdatePartyReq {

    @Schema(example = "1", description = "유저PK")private long userSeq;
    @Schema(example = "1", description = "모임PK")private long partySeq;
    @Schema(example = "축구 모임", description = "모임명")private String partyName;
    @Schema(example = "스포츠", description = "카테고리-분야")private int partyGenre;
    @Schema(example = "0101", description = "카테고리-지역")private int partyLocation;
    @Schema(example = "1", description = "카테고리-성별")private int partyGender;
    @Schema(example = "2010", description = "최소년도")private int partyMinAge;
    @Schema(example = "1990", description = "최대년도")private int partyMaxAge;
    @Schema(example = "20", description = "최대인원수")private int partyMaximum;
    @Schema(example = "1", description = "멤버모집상태")private int partyJoinGb;
    @Schema(example = "축구 하실분", description = "모임소개") private String partyIntro;
    @Schema(example = "나이와 자기소개 작성필수", description = "가입양식")private String partyJoinForm;

    @JsonIgnore
    @Schema(example = "모임 사진", description = "사진 파일 이름")
    private String partyPic;
}
