package com.green.project2nd.board;


import com.green.project2nd.board.model.BoardDeleteReq;
import com.green.project2nd.board.model.BoardPicPostDto;
import com.green.project2nd.board.model.BoardPostReq;
import com.green.project2nd.board.model.BoardPostRes;
import com.green.project2nd.common.model.CustomFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor

public class BoardService {
    private final BoardMapper mapper;
    private final CustomFileUtils customFileUtils;

    public BoardPostRes postBoard(List<MultipartFile> pics, BoardPostReq p){
        int result = mapper.postBoard(p);


        BoardPicPostDto picDto = BoardPicPostDto.builder().boardSeq(p.getBoardPartySeq()).build();
        try {
            String path = String.format("board/%d", p.getBoardPartySeq());
            customFileUtils.makeFolders(path);

            for(MultipartFile pic : pics) {
                String saveFileName = customFileUtils.makeRandomFileName(pic);
                picDto.getFileNames().add(saveFileName);
                String target = String.format("%s/%s", path, saveFileName);
                customFileUtils.transferTo(pic, target);
            }
            int affectedRowsPics = mapper.postBoardPics(picDto);

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Board 등록 오류");
        }

        return BoardPostRes.builder()
                .boardSeq(p.getBoardPartySeq())
                .pics(picDto.getFileNames())
                .build();

    }
    public int deleteBoard(BoardDeleteReq p) {
        int result = mapper.deleteBoard(p);
        if(result == 0) {
            return 2;
        }
        return result;
    }
 //   public int getBoard(BoardGetReq p) {
 //   }
}
