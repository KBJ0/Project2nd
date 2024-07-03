package com.green.project2nd.project2nd.partywish;

import com.green.project2nd.partywish.PartyWishMapper;
import com.green.project2nd.partywish.model.PartyWishToggleReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartyWishService {
    private final PartyWishMapper mapper;

    public int togglePartyWish(PartyWishToggleReq p) {
        int result = mapper.deletePartyWish(p);

        if(result == 1) {
            return 0;
        }
        return mapper.insertPartyWish(p);
    }
}
