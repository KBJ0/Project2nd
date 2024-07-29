package com.green.project2nd.partyranking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartyRankingService {
    private final PartyRankingMapper mapper;

}
