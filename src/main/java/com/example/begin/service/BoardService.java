package com.example.begin.service;


import com.example.begin.entity.Board;
import com.example.begin.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Files;
import java.util.UUID;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception {


        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();

        Path savePath = Paths.get(projectPath);

        if (!Files.exists(savePath)) {
            Files.createDirectories(savePath);
        }

        savePath = Paths.get(projectPath + "/" + fileName);

        Files.write(savePath, file.getBytes());

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }


    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){

        return boardRepository.findAll(pageable);
    }

    // 게시글 불러오기
    public Board boardview(Integer id){

        return boardRepository.findById(id).get();
    }
    //게시글 삭제
    public void boardDelete(Integer id){

        boardRepository.deleteById(id);
    }

}
