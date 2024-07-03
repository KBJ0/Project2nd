package com.green.project2nd.party;


import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.party.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/party")
@Tag(name = "party", description = "party CRUD")
public class PartyController {
    private final PartyService service;

    @PostMapping
    @Operation(summary = "모임 생성" , description = "모임 등록" )
    public ResultDto<PostPartyRes> postParty(@RequestPart MultipartFile partyPic, @RequestPart PostPartyReq p) {
        return service.postParty(partyPic, p);
    }

    @GetMapping
    @Operation(summary = "모임들 불러오기" , description = "모임 불러오기")
    public ResultDto<List<GetPartyRes>> getParty() {
        return service.getParty();
    }

    @GetMapping("/detail")
    @Operation(summary = "모임 하나 불러오기" , description = "모임 불러오기")
    public ResultDto<GetPartyRes> getPartyDetail(@RequestParam(name = "party_seq") Long partySeq) {
        return service.getPartyDetail(partySeq);
    }

    //사진 수정할 경우 어떻게 처리할지 프론트랑 의뇬 필요함
    //기본적으로 프론트에서 사진을 띄우고, 백에게 해당 코드 주는 것으로 작성함.
    @PatchMapping
    @Operation(summary = "모임 수정" , description = "모임 수정")
    public ResultDto<UpdatePartyRes> updateParty(@RequestPart MultipartFile partyPic, @RequestPart UpdatePartyReq p) {
        return service.updateParty(partyPic, p);
    }
    //관리자가 모임 등록을 승인해주는 코드,관리자가 누군지 추가하고 권한줘야함.
    //모임이 만들어지면 신청자를 모임장을 설정해줘야함(member작성하고 작업해야함.)
    //partyAuthGb 설정 했으면 해당주석들 지우기.(+코드)
    @PatchMapping("/authGb")
    @Operation(summary = "모임 생성 승인" , description = "0:미승인, 1:승인")
    public ResultDto<Integer> updatePartyAuthGb(@RequestParam(name = "party_seq") Long partySeq,
                                                @RequestParam(name = "party_auth_gb") int partyAuthGb) {
        return service.updatePartyAuthGb(partySeq, partyAuthGb);
    }

    //권한 설정 해줘야함(DB조회 or 토큰방식)
    @DeleteMapping
    @Operation(summary = "모임 삭제" , description = "모임 삭제")
    public ResultDto<Integer> deleteParty(@RequestParam(name ="party_seq") Long partySeq
            , @RequestParam(name ="member_role") String memberRole){
        return service.deleteParty(partySeq,memberRole);
    }

}
