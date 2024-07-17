package com.green.project2nd.party;
import com.green.project2nd.common.model.CustomFileUtils;
import com.green.project2nd.common.model.ResultDto;
import com.green.project2nd.party.exception.PartyExceptionHandler;
import com.green.project2nd.party.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PartyService {
    ResultDto<PostPartyRes> postParty(@Nullable MultipartFile partyPic, PostPartyReq p) throws  Exception;
    ResultDto<List<GetPartyLocationRes>> getPartyLocation(int cdSub, int cdGb);
    ResultDto<List<GetPartyRes>> getParty();
    ResultDto<GetPartyRes> getPartyDetail(Long partySeq);
    ResultDto<GetPartyRes2> getPartyMine(GetPartyReq2 p);
    ResultDto<GetPartyRes2> getPartyLeader(GetPartyReq2 p);
    ResultDto<UpdatePartyRes> updateParty(@Nullable MultipartFile partyPic, UpdatePartyReq p) throws Exception;
    ResultDto<Integer> updatePartyAuthGb(Long partySeq, Long userSeq);
    ResultDto<Integer> updatePartyForGb2(Long partySeq, Long userSeq);
}
