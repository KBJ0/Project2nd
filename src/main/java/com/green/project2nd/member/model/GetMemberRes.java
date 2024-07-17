package com.green.project2nd.member.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetMemberRes {
    @Schema(example = "1", description = "멤버 PK")
    private Long memberSeq;
    @Schema(example = "1", description = "유저 PK")
    private Long memberUserSeq;
    @Schema(example = "1", description = "멤버역할(1:모임장, 2:모임원)")
    private String memberRole;
    @Schema(example = "1", description = "멤버상태(0:비활성, 1:활성, 2:거부)")
    private int memberGb;
}
