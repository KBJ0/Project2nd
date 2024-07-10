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
    @Operation(summary = "모임 생성+(생성자=멤버 모임장으로 추가)" , description = "모임 등록" )
    public ResultDto<PostPartyRes> postParty(@RequestPart(required = false) MultipartFile partyPic
                                            , @RequestPart PostPartyReq p) throws Exception{
        return service.postParty(partyPic, p);
    }
    //test3


    @GetMapping("location")
    @Operation(summary = "지역 불러오기" , description = "모임 불러오기")
    public ResultDto<List<GetPartyLocationRes>> getPartyLocation(@RequestParam(name = "cd") String cd
                                         ,@RequestParam(name = "cd_gb") String cdGb) {
        if(cdGb == null){cdGb="00";}
        return service.getPartyLocation(cd,cdGb);
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

    @GetMapping("/mine")
    @Operation(summary = "나의 모임들 불러오기(내가 모임장인 것은 제외)" , description = "나의 모임들 불러오기(내가 모임장인 것은 제외)")
    public ResultDto<List<GetPartyRes2>> getPartyMine(@RequestParam long userSeq
            , @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        GetPartyReq2 req2 = new GetPartyReq2(page, size);
        req2.setUserSeq(userSeq);
        return service.getPartyMine(req2);
    }

    @GetMapping("/leader")
    @Operation(summary = "내가 모임장인 모임들 불러오기" , description = "내가 모임장인 모임들 불러오기")
    public ResultDto<List<GetPartyRes2>> getPartyLeader(@RequestParam long userSeq
            , @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        GetPartyReq2 req2 = new GetPartyReq2(page, size);
        req2.setUserSeq(userSeq);
        return service.getPartyLeader(req2);
    }



    @PatchMapping()
    @Operation(summary = "모임 수정" , description = "모임 수정")
    public ResultDto<UpdatePartyRes> updateParty(@RequestPart(required = false) MultipartFile partyPic
                                                , @RequestPart UpdatePartyReq p) throws Exception{
        return service.updateParty(partyPic, p);
    }
    //관리자가 모임 등록을 승인해주는 코드,관리자가 누군지 추가하고 권한줘야함. 현재는 모임장이 모임 생성 승인가능ㅋㅋ
    @PatchMapping("/authGb")
    @Operation(summary = "모임 생성 승인" , description = "0:미승인, 1:승인")
    public ResultDto<Integer> updatePartyAuthGb(@RequestParam(name = "party_seq") Long partySeq,
                                                @RequestParam(name = "user_seq") Long userSeq) {
        return service.updatePartyAuthGb(partySeq, userSeq);
    }

    @PatchMapping("/authGb2")
    @Operation(summary = "모임 삭제(휴먼,복구 기능은 X)" , description = "모임 삭제")
    public ResultDto<Integer> updatePartyForGb2(@RequestParam(name ="party_seq") Long partySeq
            , @RequestParam(name = "user_seq") Long userSeq){
        return service.updatePartyForGb2(partySeq,userSeq);
    }

//    // 모임안의 정보를 순서대로 다 지워야함. 아직 미추가함.
//    @DeleteMapping
//    @Operation(summary = "모임 삭제" , description = "모임 삭제")
//    public ResultDto<Integer> deleteParty(@RequestParam(name ="party_seq") Long partySeq
//            , @RequestParam(name = "user_seq") Long userSeq){
//        return service.deleteParty(partySeq,userSeq);
//    }

}
