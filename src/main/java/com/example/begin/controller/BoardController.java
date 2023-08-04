package com.example.begin.controller;


import com.example.begin.entity.Board;
import com.example.begin.repository.BoardRepository;
import com.example.begin.service.BoardService;
import com.example.begin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/board/write")
    public String boardWriteForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // 로그인 세션이 없을 경우 경고창을 띄우고 home 페이지로 이동
            model.addAttribute("message", "글쓰기를 하시려면 먼저 로그인해주세요.");
            model.addAttribute("searchUrl", "/");
            return "message";
        }
        return "boardwrite";
    }
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws  Exception{

        boardService.write(board, file);

        model.addAttribute("message","글 작성이 완료됨");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }
    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault (page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
                            Pageable pageable){
        Page<Board> list= boardService.boardList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage -4,1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());
        model.addAttribute("list",list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "boardlist";
    }

    @GetMapping("/board/view") //localhost:8080/board/view?id=1

    public String boardview(Model model, Integer id){

        model.addAttribute("board", boardService.boardview(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id, Model model){

        boardService.boardDelete(id);

        model.addAttribute("message", "글 삭제 완료됨");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }


    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){

        model.addAttribute("board", boardService.boardview(id));

        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String update(Board board, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        // 수정된 이미지가 있다면 업로드하고 기존 이미지 삭제
        if (file != null && !file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            Path savePath = Paths.get(projectPath);
            if (!Files.exists(savePath)) {
                Files.createDirectories(savePath);
            }
            savePath = Paths.get(projectPath + "/" + fileName);
            Files.write(savePath, file.getBytes());
            // 기존 이미지 파일 삭제
            String oldFilePath = board.getFilepath();
            if (oldFilePath != null) {
                Path oldPath = Paths.get(projectPath + "/" + oldFilePath.substring(7));
                if (Files.exists(oldPath)) {
                    Files.delete(oldPath);
                }
            }
            board.setFilename(fileName);
            board.setFilepath("/files/" + fileName);
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }



}
