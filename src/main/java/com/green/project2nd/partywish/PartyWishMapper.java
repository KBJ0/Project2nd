package com.green.project2nd.partywish;

import com.green.project2nd.partywish.model.PartyWishGetListReq;
import com.green.project2nd.partywish.model.PartyWishGetListRes;
import com.green.project2nd.partywish.model.PartyWishToggleReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PartyWishMapper {
    int insertPartyWish(PartyWishToggleReq p);

    int deletePartyWish(PartyWishToggleReq p);

    List<PartyWishGetListRes> partyWishGetList(long userSeq);

}
