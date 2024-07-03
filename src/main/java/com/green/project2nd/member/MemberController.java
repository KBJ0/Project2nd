package com.green.project2nd.member;


import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.member.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.green.project2nd.user.userexception.ConstMessage.p;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/member")
@Tag(name = "member", description = "member CRUD")
public class MemberController {
    private final MemberService service;

    @PostMapping("/{party_seq}")
    @Operation(summary = "멤버 등록" , description = "모임 등록" )
    public ResultDto<Integer> postMember(@PathVariable("party_seq") Long memberPartySeq,
                                         @RequestParam(name = "member_user_seq") Long memberUserSeq) {
        return service.postMember(memberPartySeq, memberUserSeq);
    }

    @GetMapping("/{party_seq}")
    @Operation(summary = "멤버들 불러오기" , description = "모임 불러오기")
    public ResultDto<List<GetMemberRes>> getMember(@PathVariable("party_seq") Long memberPartySeq) {
        return service.getMember(memberPartySeq);
    }
    @GetMapping("/detail/{party_seq}")
    @Operation(summary = "멤버 한명 불러오기" , description = "모임 불러오기")
    public ResultDto<GetMemberRes> getMemberDetail(@PathVariable("party_seq") Long memberPartySeq,
                                                 @RequestParam(name = "member_user_seq") Long memberUserSeq) {
        return service.getMemberDetail(memberPartySeq, memberUserSeq);
    }

    @PatchMapping("/{party_seq}")
    @Operation(summary = "멤버 권한 수정" , description = "모임 수정")
    public ResultDto<UpdateMemberRes> updateMember(@PathVariable("party_seq") Long memberPartySeq,
                                                   @RequestBody UpdateMemberReq p){
        return service.updateMember(memberPartySeq, p);
    }

    @PatchMapping("/gb/{party_seq}")
    @Operation(summary = "멤" , description = "0:미승인, 1:승인")
    public ResultDto<UpdateMemberRes> updateMemberGb(@PathVariable(name = "party_seq") Long memberPartySeq,
                                                     @RequestParam(name = "member_user_seq") Long memberUserSeq) {
        return service.updateMemberGb(memberPartySeq, memberUserSeq);
    }

    @DeleteMapping("/{party_seq}")
    @Operation(summary = "모임 삭제" , description = "모임 삭제")
    public ResultDto<Integer> deleteMember(@PathVariable(name = "party_seq") Long memberPartySeq,
                                           @RequestParam(name = "member_user_seq") Long memberUserSeq){
        return service.deleteMember(memberPartySeq, memberUserSeq);
    }

}
