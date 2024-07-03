package com.green.project2nd.project2nd.party;


import com.green.project2nd.common.model.CustomFileUtils;
import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.party.PartyMapper;
import com.green.project2nd.party.exception.PartyExceptionHandler;
import com.green.project2nd.party.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PartyService {
    private final PartyMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final PartyExceptionHandler check;

    @Transactional
    public ResultDto<PostPartyRes> postParty(MultipartFile partyPic, PostPartyReq p){
        check.exception(partyPic,p); // 해당코드는 아래 주석과 같다.
        // if (partySeq == null || partySeq == 0) {return ResultDto.resultDto("NPS", "존재하지 않는 모임입니다.");}
        // if (mapper.checkPartySeq(partySeq) == 0) {return ResultDto.resultDto("NP", "존재하지 않는 모임입니다.");}
        // if (partyPic == null) {throw new NullPointerException();}
        String saveFileName = customFileUtils.makeRandomFileName(partyPic);
        String path = String.format("party/%d", p.getPartySeq());
        customFileUtils.makeFolders(path); // 폴더 생성
        if(partyPic != null && !partyPic.isEmpty()){
            String target = String.format("%s/%s", path, saveFileName);
            try {
                customFileUtils.transferTo(partyPic, target); // 사진 이름 지정 후 파일에 저장
            } catch (Exception e) {
                log.error("파일 전송 중 오류 발생", e);
                throw new RuntimeException();
            }
        }
        mapper.postParty(p);
        p.setPartyPic(saveFileName);
        return ResultDto.resultDto("SU","모임 생성."
                , PostPartyRes.builder()
                .partySeq(p.getPartySeq())
                .partyPic(p.getPartyPic())
                .build());
    }

    public ResultDto<List<GetPartyRes>> getParty() {
        return ResultDto.resultDto("SU", "모임들 불러오기.", mapper.getParty());
    }
    public ResultDto<GetPartyRes> getPartyDetail(Long partySeq) {
        check.exception(partySeq); // 해당코드는 아래 주석과 같다.
        // if (partySeq == null || partySeq == 0) {return ResultDto.resultDto("NPS", "존재하지 않는 모임입니다.");}
        // if (mapper.checkPartySeq(partySeq) == 0) {return ResultDto.resultDto("NP", "존재하지 않는 모임입니다.");}
        return ResultDto.resultDto("SU", "모임 하나 불러오기.", mapper.getPartyDetail(partySeq));
    }

    @Transactional
    public ResultDto<UpdatePartyRes> updateParty(MultipartFile partyPic, UpdatePartyReq p) {
        check.exception(partyPic, p);
        // 해당코드는 아래 주석과 같다.
        // if (partySeq == null || partySeq == 0) {return ResultDto.resultDto("NPS", "존재하지 않는 모임입니다.");}
        // if (mapper.checkPartySeq(partySeq) == 0) {return ResultDto.resultDto("NP", "존재하지 않는 모임입니다.");}
        // if (partyPic == null) {throw new NullPointerException();}

        String path = String.format("party/%d", p.getPartySeq());
        String saveFileName = customFileUtils.makeRandomFileName(partyPic);
        String target = String.format("%s/%s", path, saveFileName);
        try {
            customFileUtils.deleteFolder(path);
            customFileUtils.makeFolders(path);
            customFileUtils.transferTo(partyPic, target); // 사진 이름 지정 후 파일에 저장
        } catch (Exception e) {
            log.error("파일 전송 중 오류 발생", e);
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
        }
        p.setPartyPic(saveFileName);
        mapper.updateParty(p);
        UpdatePartyRes res = new UpdatePartyRes();
        res.setPartyPic(p.getPartyPic());
        return ResultDto.resultDto("SU","모임 수정.", res);
    }
    //partyAuthGb 설정 했으면 해당주석들 지우기.(+코드)
    public ResultDto<Integer> updatePartyAuthGb(Long partySeq, int partyAuthGb) {
        check.exception(partySeq); // 해당코드는 아래 주석과 같다.
        // if (partySeq == null || partySeq == 0) {return ResultDto.resultDto("NPS", "존재하지 않는 모임입니다.");}
        // if (mapper.checkPartySeq(partySeq) == 0) {return ResultDto.resultDto("NP", "존재하지 않는 모임입니다.");}
        if(partyAuthGb != 1){return ResultDto.resultDto("SU","모임 등록을 거절 하였습니다.");}
        mapper.updatePartyAuthGb(partySeq, partyAuthGb);
        return ResultDto.resultDto("SU","모임 생성 승인");
    }

    //권한 설정 해줘야함(DB조회 or 토큰방식)
    public ResultDto<Integer> deleteParty(Long partySeq, String memberRole) {
        check.exception(partySeq); // 해당코드는 아래 주석과 같다.
        // if (partySeq == null || partySeq == 0) {return ResultDto.resultDto("NPS", "존재하지 않는 모임입니다.");}
        // if (mapper.checkPartySeq(partySeq) == 0) {return ResultDto.resultDto("NP", "존재하지 않는 모임입니다.");}

        //해당 코드는 임시 코드임 DDA : Don't have Delete Authority
        if (!memberRole.equals("멤버장")){return ResultDto.resultDto("DDA", "삭제 권한이 없습니다.");}
        customFileUtils.deleteFolder("party/" + partySeq);
        mapper.deleteParty(partySeq);
        return ResultDto.resultDto("SU","모임 삭제.");
    }
}
