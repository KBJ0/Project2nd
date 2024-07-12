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
    public PostReviewRes postReview(List<MultipartFile> pics, PostReviewReq p) throws Exception{
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
    public List<String> patchReview(List<MultipartFile> pics, PatchReviewReq p) throws Exception{
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

    public GetReviewAllPageRes getReviewAll(GetReviewAllReq p) {
        List<GetReviewAllRes> res = mapper.getReviewAll(p);
        GetReviewAllPageRes pageRes = new GetReviewAllPageRes(
                res
                , mapper.getTotalElements(p.getSearch(),p.getSearchData(), 0)
                , p.getSize()
        );
        return pageRes;
    }

    public GetReviewUserPageRes getReviewUser(GetReviewUserReq p) {
        List<GetReviewUserRes> res = mapper.getReviewUser(p);
        GetReviewUserPageRes pageRes = new GetReviewUserPageRes(
                res
                , mapper.getTotalElements(p.getSearch(),p.getSearchData(), p.getUserSeq())
                , p.getSize()
        );
        return pageRes;
    }

    public PostReviewPicDto postPics(long reviewSeq, List<MultipartFile> pics, String path) throws Exception {
        PostReviewPicDto ppic = PostReviewPicDto.builder()
                .reviewSeq(reviewSeq)
                .build();

        customFileUtils.makeFolders(path);
        for(MultipartFile pic : pics) {
            String fileName = customFileUtils.makeRandomFileName(pic);
            String target = String.format("%s/%s", path, fileName);
            customFileUtils.transferTo(pic, target);
            ppic.getFileNames().add(fileName);
        }
        mapper.postReviewPics(ppic);

        return ppic;
    }
}
