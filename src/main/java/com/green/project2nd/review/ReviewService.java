package com.green.project2nd.review;

import com.green.project2nd.common.model.CustomFileUtils;
import com.green.project2nd.review.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewMapper mapper;
    private final CustomFileUtils customFileUtils;
    private String path = "review/";

    @Transactional
    public PostReviewRes postReview(List<MultipartFile> pics, PostReviewReq p) {
        mapper.postReview(p);

        if(pics == null) {
            return PostReviewRes.builder()
                    .reviewSeq(p.getReviewSeq())
                    .build();
        }

       PostReviewPicDto ppic = postPics(p.getReviewSeq(), pics, path+p.getReviewSeq());

        return PostReviewRes.builder()
                .reviewSeq(ppic.getReviewSeq())
                .pics(ppic.getFileNames())
                .build();
    }

    @Transactional
    public List<String> patchReview(List<MultipartFile> pics, PatchReviewReq p) {
        mapper.deleteReviewPics(p.getReviewSeq());
        mapper.patchReview(p);
        customFileUtils.deleteFolder(path + p.getReviewSeq());

        if(pics == null) {
            return null;
        }

        PostReviewPicDto ppic = postPics(p.getReviewSeq(), pics, path+p.getReviewSeq());
        return mapper.getPics(ppic.getReviewSeq());
    }

    @Transactional
    public int deleteReview(long reviewSeq) {
        mapper.deleteReviewPics(reviewSeq);
        mapper.deleteReviewFavs(reviewSeq);
        return mapper.deleteReview(reviewSeq);
    }

    public List<GetReviewAllRes> getReviewAll(GetReviewAllReq p) {
        return mapper.getReviewAll(p);
    }

    public List<GetReviewUserRes> getReviewUser(GetReviewUserReq p) {
        return mapper.getReviewUser(p);
    }


    public PostReviewPicDto postPics(long reviewSeq, List<MultipartFile> pics, String path) {
        PostReviewPicDto ppic = PostReviewPicDto.builder()
                .reviewSeq(reviewSeq)
                .build();
        try {
            customFileUtils.makeFolders(path);
            for(MultipartFile pic : pics) {
                String fileName = customFileUtils.makeRandomFileName(pic);
                String target = String.format("%s/%s", path, fileName);
                customFileUtils.transferTo(pic, target);
                ppic.getFileNames().add(fileName);
            }
            mapper.postReviewPics(ppic);
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("사진 등록 오류");
        }
        return ppic;
    }
}