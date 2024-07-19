package com.green.project2nd.partywish;

import com.green.project2nd.partywish.model.PartyWishGetListRes;
import com.green.project2nd.partywish.model.PartyWishToggleReq;
import com.green.project2nd.security.AuthenticationFacade;
import com.green.project2nd.user.userexception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.green.project2nd.user.userexception.ConstMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartyWishService {
    private final PartyWishMapper mapper;
    private final AuthenticationFacade authenticationFacade;

    public int togglePartyWish(PartyWishToggleReq p) throws Exception {
        try {
            int result = mapper.deletePartyWish(p);
            if(result == 1) {
                return 0;
            }
        } catch (NotFoundException ne) {
            throw new NotFoundException(FAILURE_MESSAGE);
        } catch (Exception e) {
            throw new Exception(ERROR_MESSAGE);
        }
        return mapper.insertPartyWish(p);
    }

    public List<PartyWishGetListRes> partyWishGetList(long userSeq) {
        try {
            return mapper.partyWishGetList(userSeq);
        } catch (RuntimeException re) {
            throw new RuntimeException(TRY_AGAIN_MESSAGE);
        }
    }
}
