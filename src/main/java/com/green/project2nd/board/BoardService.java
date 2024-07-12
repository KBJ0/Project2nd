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
        mapper.deleteBoardPics(p.getBoardSeq());
        return mapper.deleteBoard(p.getBoardSeq());
    }

    public int boardPatch(List<MultipartFile> newPics, BoardPatchReq p) {
        mapper.patchBoard(p);

        if(newPics == null || p.getDeleteFileNames() == null) {
            return mapper.deleteBoard(p.getBoardSeq());}

        BoardPicPostDto dto = BoardPicPostDto.builder().boardSeq(p.getBoardSeq()).
                fileNames(new ArrayList()).build();
        try{
            String path = String.format("board/%d", p.getBoardSeq());

            if(p.getDeleteFileNames() != null) {
                for(String fileName : p.getDeleteFileNames()) {
                    String target = String.format("%s/%s" , path, fileName);
                    customFileUtils.deleteFolder(target);
                    mapper.deleteBoardPics(p.getBoardSeq());
                }
            }

            if(newPics != null) {
                for(MultipartFile pic : newPics){
                    String fileName = customFileUtils.makeRandomFileName(pic);
                    String target = String.format("%s/%s" , path, fileName);
                    customFileUtils.transferTo(pic, target);
                    dto.getFileNames().add(fileName);
                }
                mapper.postBoardPics(dto);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("board 수정 오류");
        }
        return mapper.deleteBoard(p.getBoardSeq());

    }
}
