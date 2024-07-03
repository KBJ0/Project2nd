package com.green.project2nd.project2nd.partywish;

import com.green.project2nd.partywish.model.PartyWishToggleReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PartyWishMapper {
    int insertPartyWish(PartyWishToggleReq p);

    int deletePartyWish(PartyWishToggleReq p);

}
