package com.green.project2nd.partywish;

import com.green.project2nd.partywish.model.PartyWishToggleReq;
import com.green.project2nd.user.userexception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.green.project2nd.user.userexception.ConstMessage.ERROR_Message;
import static com.green.project2nd.user.userexception.ConstMessage.FAILURE_Message;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartyWishService {
    private final PartyWishMapper mapper;

    public int togglePartyWish(PartyWishToggleReq p) throws Exception {
        try {
            int result = mapper.deletePartyWish(p);
            if(result == 1) {
                return 0;
            }
        } catch (NotFoundException ne) {
            throw new NotFoundException(FAILURE_Message);
        } catch (Exception e) {
            throw new Exception(ERROR_Message);
        }
        return mapper.insertPartyWish(p);
    }
}
