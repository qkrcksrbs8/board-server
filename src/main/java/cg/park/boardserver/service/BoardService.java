package cg.park.boardserver.service;

import cg.park.boardserver.model.Board;
import cg.park.boardserver.model.User;
import cg.park.boardserver.repository.BoardRepository;
import cg.park.boardserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board) {
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }

}
