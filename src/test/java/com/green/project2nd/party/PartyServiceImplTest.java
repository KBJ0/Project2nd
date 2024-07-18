package com.green.project2nd.party;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@Import({ PartyServiceImpl.class })
class PartyServiceImplTest {

    @MockBean PartyMapper mapper;
    @Autowired PartyService service;

    @Test
    @DisplayName("모임 생성 + 모임장 등록")
    void postParty() {
    }

    @Test
    @DisplayName("지역 불러오기")
    void getPartyLocation() {
    }

    @Test
    @DisplayName("모임들 불러오기")
    void getParty() {
    }

    @Test
    @DisplayName("모임 하나 불러오기")
    void getPartyDetail() {
    }

    @Test
    @DisplayName("나의 모임들 불러오기(내가 모임장인건 제외)")
    void getPartyMine() {
    }

    @Test
    @DisplayName("내가 모임장인 모임들 불러오기")
    void getPartyLeader() {
    }

    @Test
    @DisplayName("모임 정보 수정")
    void updateParty() {
    }

    @Test
    @DisplayName("모임 생성 승인")
    void updatePartyAuthGb() {
    }

    @Test
    @DisplayName("모임 삭제(휴먼,복구 기능은 X)")
    void updatePartyForGb2() {
    }
}