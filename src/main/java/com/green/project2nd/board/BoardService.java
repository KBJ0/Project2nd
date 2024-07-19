package com.green.project2nd.board;


import com.green.project2nd.board.model.*;
import com.green.project2nd.common.model.CustomFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor

public class BoardService {
    private final BoardMapper mapper;
    private final CustomFileUtils customFileUtils;


    public BoardPostRes postBoard(List<MultipartFile> pics, BoardPostReq p) {
        mapper.postBoard(p);
        if( pics == null || pics.size() == 0 )
        {return BoardPostRes.builder().boardSeq(p.getBoardSeq()).build();}

        BoardPicPostDto dto = BoardPicPostDto.builder().boardSeq(p.getBoardSeq()).
                fileNames(new ArrayList()).build();
        try {
            String path = String.format("board/%d", p.getBoardSeq());
            customFileUtils.makeFolders(path);
            for(MultipartFile pic : pics){
                String fileName = customFileUtils.makeRandomFileName(pic);
                String target = String.format("%s/%s" , path, fileName);
                customFileUtils.transferTo(pic, target);
                dto.getFileNames().add(fileName);
            }
            mapper.postBoardPics(dto);

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("게시글 등록 오류");
        }

        return BoardPostRes.builder()
                .boardSeq(dto.getBoardSeq())
                .pics(dto.getFileNames())
                .build();
    }

    public int deleteBoard(BoardDeleteReq p) {
        List<String> fileNames = mapper.getFileNamesByBoardSeq(p.getBoardSeq());
        for (String fileName : fileNames) {
            mapper.deleteBoardPics(p.getBoardSeq(), fileName);
        }
        return mapper.deleteBoard(p.getBoardSeq(), p.getBoardMemberSeq());
    }


    public boolean boardPatch(List<MultipartFile> newPics, BoardPatchReq p) {
        int updateCount = mapper.patchBoard(p);
        if (updateCount == 0) {
            throw new RuntimeException("수정된 부분이 없음");
        }

        List<String> existingFileNames = mapper.getFileNamesByBoardSeq(p.getBoardSeq());

        boolean hasNewFiles = newPics != null && !newPics.isEmpty();
        boolean hasDeletedFiles = p.getDeleteFileNames() != null && !p.getDeleteFileNames().isEmpty();

        if (!hasNewFiles && !hasDeletedFiles) {
            return true;
        }

        BoardPicPostDto dto = BoardPicPostDto.builder().boardSeq(p.getBoardSeq())
                .fileNames(new ArrayList<>()).build();
        try {
            String path = String.format("board/%d", p.getBoardSeq());

            if (hasDeletedFiles) {
                for (String fileName : p.getDeleteFileNames()) {
                    if (existingFileNames.contains(fileName)) {
                        String target = String.format("%s/%s", path, fileName);
                        customFileUtils.deleteFolder(target);
                        mapper.deleteBoardPics(p.getBoardSeq(), fileName);
                    }
                }
            }
            if (hasNewFiles) {
                for (MultipartFile pic : newPics) {
                    String fileName = customFileUtils.makeRandomFileName(pic);
                    String target = String.format("%s/%s", path, fileName);
                    customFileUtils.transferTo(pic, target);
                    dto.getFileNames().add(fileName);
                }
                mapper.postBoardPics(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("board 수정 오류");
        }
        return true;
    }
    public BoardGetRes getBoard(long boardSeq) {
        mapper.incrementBoardHit(boardSeq);
        BoardGetRes board = mapper.getBoard(boardSeq);
        List<String> pics = mapper.getFileNamesByBoardSeq(boardSeq);
        board.setPics(pics);
        return board;
    }

    public BoardGetPage getBoardList(BoardGetReq data) {
        List<BoardGetRes> list = mapper.getBoardList(data);
        long totalElements = mapper.getTotalCount();

        for (BoardGetRes board : list) {
            List<String> pics = mapper.getFileNamesByBoardSeq(board.getBoardSeq());
            board.setPics(pics);
        }

        return new BoardGetPage(list, data.getSize(), totalElements);
    }
}
