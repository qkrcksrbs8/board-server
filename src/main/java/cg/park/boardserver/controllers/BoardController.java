package cg.park.boardserver.controllers;

import cg.park.boardserver.model.Board;
import cg.park.boardserver.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;
    
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("boards", boardRepository.findAll());
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        model.addAttribute("board", (id == null)? new Board() : boardRepository.findById(id).orElse(null));
        return "board/form";
    }

    @PostMapping("/form")
    public String greetingSubmit(@ModelAttribute Board board) {
        boardRepository.save(board);
        return "redirect:/board/list";
    }

}
