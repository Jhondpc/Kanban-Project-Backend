package com.example.kanban_backend.service;

import com.example.kanban_backend.model.BoardEntity;
import com.example.kanban_backend.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public List<BoardEntity> getAllBoards() {
        return boardRepository.findAll();
    }

    public List<BoardEntity> getBoardByUserId(Integer id) {
        return boardRepository.findByUserId(id);
    }

    public BoardEntity saveBoard(BoardEntity board) {
        return boardRepository.save(board);
    }

    public void deleteBoard(Integer boardId) { boardRepository.deleteById(boardId);}

    public BoardEntity insertBoardsNewUser(Integer userId) {
        return boardRepository.insertBoardsNewUser(userId);
    }

    public List<BoardEntity> updateBoards(Integer userId, List<BoardEntity> boards) {
        return boardRepository.saveAll(boards);
    }

    @Transactional
    public void updateBoardName(Integer boardId, String boardName) {
        boardRepository.updateBoardName(boardId, boardName);
    }
}
