package com.green.project2nd.party;
import com.green.project2nd.common.model.CustomFileUtils;
import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.party.exception.PartyExceptionHandler;
import com.green.project2nd.party.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PartyService {
    private final PartyMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final PartyExceptionHandler check;

    public ResultDto<PostPartyRes> postParty(@Nullable MultipartFile partyPic, PostPartyReq p) throws Exception{
        check.exception(partyPic,p);

        String saveFileName = customFileUtils.makeRandomFileName(partyPic);
        p.setPartyPic(saveFileName);
        mapper.postParty(p);
        String path = String.format("party/%d", p.getPartySeq());
        customFileUtils.makeFolders(path); // 폴더 생성
        String target = String.format("%s/%s", path, saveFileName);
        customFileUtils.transferTo(partyPic, target); // 사진 이름 지정 후 파일에 저장

        mapper.postMemberForPostParty(p);
        return ResultDto.resultDto(1,"모임을 생성하였습니다.", PostPartyRes.builder().partySeq(p.getPartySeq()).partyPic(p.getPartyPic()).build());
    }

    public ResultDto<List<GetPartyLocationRes>> getPartyLocation(String cdSub, String cdGb) {
        if(!cdGb.equals("00")){
            try {
                return ResultDto.resultDto(1,"지역을 불러왔습니다.",mapper.getPartyLocation(cdSub,cdGb));
            }catch (RuntimeException e){
                log.info("e: " + e);
                return ResultDto.resultDto(2,"처리할 수 없는 요청입니다.",mapper.getPartyLocation(cdSub,cdGb));
            }
        }
        return ResultDto.resultDto(1,"지역들을 불러왔습니다.",mapper.getPartyLocationAll(cdSub));
    }
    public ResultDto<List<GetPartyRes>> getParty() {
        return ResultDto.resultDto(1, "모임들을 불러왔습니다.", mapper.getParty());
    }
    public ResultDto<GetPartyRes> getPartyDetail(Long partySeq) {
        check.exception(partySeq);
        return ResultDto.resultDto(1, "하나의 모임을 불러왔습니다.", mapper.getPartyDetail(partySeq));
    }
    public ResultDto<List<GetPartyRes2>> getPartyMine(GetPartyReq2 p) {
        check.exceptionUser(p.getUserSeq());
        double pageLength = (double)mapper.getPartyMineCount(p.getUserSeq())/p.getSize();
        return ResultDto.resultDto(1, "나의 모임들을 불러왔습니다.(내가 모임장인 것은 제외)", ""+(int)Math.ceil(pageLength),mapper.getPartyMine(p));
    }
    public ResultDto<List<GetPartyRes2>> getPartyLeader(GetPartyReq2 p) {
        check.exceptionUser(p.getUserSeq());
        double pageLength = (double)mapper.getPartyLeaderCount(p.getUserSeq())/p.getSize();
        return ResultDto.resultDto(1, "내가 모임장인 모임들을 불러왔습니다.",""+(int)Math.ceil(pageLength), mapper.getPartyLeader(p));
    }

    public ResultDto<UpdatePartyRes> updateParty(@Nullable MultipartFile partyPic, UpdatePartyReq p) throws Exception {
        check.exception(partyPic, p);
        String path = String.format("party/%d", p.getPartySeq());
        String saveFileName = customFileUtils.makeRandomFileName(partyPic);
        String target = String.format("%s/%s", path, saveFileName);

        customFileUtils.deleteFolder(path);
        customFileUtils.makeFolders(path);
        customFileUtils.transferTo(partyPic, target); // 사진 이름 지정 후 파일에 저장

        p.setPartyPic(saveFileName);
        mapper.updateParty(p);
        UpdatePartyRes res = new UpdatePartyRes();
        res.setPartyPic(p.getPartyPic());
        return ResultDto.resultDto(1,"모임을 수정하였습니다.", res);
    }

    //partyAuthGb 설정 했으면 해당주석들 지우기.(+코드)
    public ResultDto<Integer> updatePartyAuthGb(Long partySeq, Long userSeq) {
        check.exception(partySeq, userSeq);
        int result = mapper.getPartyAuthGb(partySeq);
        mapper.updatePartyAuthGb(partySeq, userSeq);
        if (result == 1){return ResultDto.resultDto(1,"모임 생성 승인을 취소 하였습니다");}
        return ResultDto.resultDto(1,"모임 생성을 승인 하였습니다.");
    }

    public ResultDto<Integer> updatePartyForGb2(Long partySeq, Long userSeq) {
        check.exception(partySeq, userSeq);
        mapper.updatePartyForGb2(partySeq);
        return ResultDto.resultDto(1,"모임을 삭제(휴먼) 하였습니다.");
    }

//    public ResultDto<Integer> deleteParty(Long partySeq, Long userSeq) {
//        check.exception(partySeq,userSeq);
//        mapper.deletePartyMember(partySeq);
//        mapper.deleteParty(partySeq);
//        customFileUtils.deleteFolder("party/" + partySeq);
//        return ResultDto.resultDto(1,"모임을 삭제하였습니다.");
//    }



}
