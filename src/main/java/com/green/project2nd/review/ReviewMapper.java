package com.green.project2nd.review;

import com.green.project2nd.review.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    int postReview(PostReviewReq p);
    void postReviewPics(PostReviewPicDto p);
    int deleteReview(long reviewSeq);
    void deleteReviewPics(long reviewSeq);
    void deleteReviewFavs(long reviewSeq);
    List<GetReviewAllRes> getReviewAll(GetReviewAllReq p);
    List<GetReviewUserRes> getReviewUser(GetReviewUserReq p);
    void patchReview(PatchReviewReq p);
    List<String> getPics(long reviewSeq);
}