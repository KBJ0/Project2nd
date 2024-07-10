package com.green.project2nd.member;


import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.member.model.GetMemberRes;
import com.green.project2nd.member.model.UpdateMemberReq;
import com.green.project2nd.member.model.UpdateMemberRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/member")
@Tag(name = "member", description = "member CRUD")
public class MemberController {
    private final MemberService service;

//    @PostMapping("/{party_seq}")
//    @Operation(summary = "멤버 등록" , description = "모임 등록" )
//    public ResultDto<Integer> postMember(@PathVariable("party_seq") Long memberPartySeq,
//                                         @RequestParam(name = "member_user_seq") Long memberUserSeq) {
//        return service.postMember(memberPartySeq, memberUserSeq);
//    }

    @GetMapping("/{partySeq}")
    @Operation(summary = "멤버들 불러오기" , description = "멤버들 불러오기")
    public ResultDto<List<GetMemberRes>> getMember(@PathVariable("partySeq") Long memberPartySeq) {
        return service.getMember(memberPartySeq);
    }
    @GetMapping("/detail/{partySeq}")
    @Operation(summary = "멤버 한명 불러오기" , description = "멤버 한명 불러오기")
    public ResultDto<GetMemberRes> getMemberDetail(@PathVariable("partySeq") Long memberPartySeq,
                                                 @RequestParam(name = "memberUserSeq") Long memberUserSeq) {
        return service.getMemberDetail(memberPartySeq, memberUserSeq);
    }

    @PatchMapping("/{partySeq}")
    @Operation(summary = "멤버 역할 수정(미구현. 멤버 세분화 필요함.)" , description = "멤버 역할 수정")
    public ResultDto<UpdateMemberRes> updateMember(@PathVariable("partySeq") Long memberPartySeq,
                                                   @RequestBody UpdateMemberReq p){
        return service.updateMember(memberPartySeq, p);
    }

//    @PatchMapping("/gb/{party_seq}")
//    @Operation(summary = "멤버 권한 수정" , description = "0:미승인, 1:승인")
//    public ResultDto<UpdateMemberRes> updateMemberGb(@PathVariable(name = "party_seq") Long memberPartySeq,
//                                                     @RequestParam(name = "member_user_seq") Long memberUserSeq){
//        return service.updateMemberGb(memberPartySeq, memberUserSeq);
//    }
    @PatchMapping("/gb/{partySeq}")
    @Operation(summary = "멤버 권한 수정" , description = "0:미승인, 1:승인")
    public ResultDto<UpdateMemberRes> updateMemberGb(@PathVariable(name = "partySeq") Long memberPartySeq,
                                                     @RequestParam(name = "memberUserSeq") Long memberUserSeq,
                                                     @RequestParam(name = "memberLeaderUserSeq") Long memberLeaderUserSeq) {
        return service.updateMemberGb(memberPartySeq, memberUserSeq, memberLeaderUserSeq);
    }



//    @DeleteMapping("/{party_seq}")
//    @Operation(summary = "모든 멤버 삭제" , description = "모임 삭제")
//    public ResultDto<Integer> deleteMember(@PathVariable(name = "party_seq") Long memberPartySeq,
//                                           @RequestParam(name = "member_user_seq") Long memberUserSeq){
//        return service.deleteMember(memberPartySeq, memberUserSeq);
//    }

}
