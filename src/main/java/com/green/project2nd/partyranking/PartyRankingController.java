package com.green.project2nd.partyranking;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/ranking")
@Tag(name = "Ranking(모임 랭킹)", description = "랭킹 조건별 1위~3위 조회")
public class PartyRankingController {
    private final PartyRankingService service;

}
