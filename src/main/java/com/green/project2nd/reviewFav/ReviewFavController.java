package com.green.project2nd.reviewFav;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review/fav")
@Tag(name = "리뷰 도움돼요", description = "리뷰 도움돼요 Toggle")
public class ReviewFavController {

}
