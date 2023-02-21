package cg.park.boardserver.controllers;

import cg.park.boardserver.model.Board;
import cg.park.boardserver.repository.BoardRepository;
import cg.park.boardserver.service.BoardService;
import cg.park.boardserver.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardValidator boardValidator;
    
    @GetMapping("/list")
    public String list(Model model
                    , @PageableDefault(size = 2) Pageable pageable
                    , @RequestParam(required = false, defaultValue = "") String searchText) {
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage  = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        model.addAttribute("board", (id == null)? new Board() : boardRepository.findById(id).orElse(null));
        return "board/form";
    }

    @PostMapping("/form")
    public String greetingSubmit(
            @Valid Board board
            , BindingResult bindingResult
            , Authentication authentication
    ) {
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors())
            return "board/form";

        String username = authentication.getName();
        boardService.save(username, board);
        boardRepository.save(board);
        return "redirect:/board/list";
    }

}
