package com.green.project2nd.join;


import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.join.model.GetJoinRes;
import com.green.project2nd.join.model.PostJoinReq;
import com.green.project2nd.join.model.UpdateJoinGbReq;
import com.green.project2nd.join.model.UpdateJoinReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/join")
@Tag(name = "join", description = "join CRUD")
public class JoinController {
    private final JoinService service;

    //해당 모임에 신청한 신청서가 있는지 확인 필요함.(신청서 하나 가져오기 쓸 예정.)
    @PostMapping("/{joinPartySeq}")
    @Operation(summary = "신청서 등록" , description = "신청서 등록" )
    public ResultDto<Integer> postJoin(@PathVariable("joinPartySeq") Long joinPartySeq,
                                         @RequestBody PostJoinReq p) {
        return service.postJoin(joinPartySeq, p);
    }

    @GetMapping("/{joinPartySeq}")
    @Operation(summary = "신청서들 불러오기" , description = "신청서들 불러오기")
    public ResultDto<List<GetJoinRes>> getJoin(@PathVariable("joinPartySeq") Long joinPartySeq,
                                               @RequestParam(name = "leaderUserSeq") Long leaderUserSeq) {
        return service.getJoin(joinPartySeq,leaderUserSeq);
    }
    @GetMapping("/detail/{joinPartySeq}")
    @Operation(summary = "신청서 하나 불러오기" , description = "신청서 하나 불러오기")
    public ResultDto<GetJoinRes> getJoinDetail(@PathVariable("joinPartySeq") Long joinPartySeq,
                                               @RequestParam(name = "joinUserSeq") Long joinUserSeq) {
        return service.getJoinDetail(joinPartySeq, joinUserSeq);
    }

    @PatchMapping("/{joinPartySeq}")
    @Operation(summary = "신청서 수정" , description = "신청서 수정")
    public ResultDto<Integer> updateJoin(@PathVariable("joinPartySeq") Long joinPartySeq,
                                               @RequestBody UpdateJoinReq p){
        return service.updateJoin(joinPartySeq, p);
    }
    @PatchMapping("gb/{joinPartySeq}")
    @Operation(summary = "신청서 승인(승인된 유저=멤버로 추가)" , description = "신청서 승인")
    public ResultDto<Integer> updateJoinGb(@PathVariable("joinPartySeq") Long joinPartySeq,
                                           @RequestBody UpdateJoinGbReq p){
        return service.updateJoinGb(joinPartySeq, p);
    }

    @DeleteMapping("/{joinPartySeq}")
    @Operation(summary = "신청서 삭제" , description = "모임 삭제")
    public ResultDto<Integer> deleteJoin(@PathVariable(name = "joinPartySeq") Long joinPartySeq,
                                         @RequestParam(name = "joinUserSeq") Long joinUserSeq){
        return service.deleteJoin(joinPartySeq, joinUserSeq);
    }

}
