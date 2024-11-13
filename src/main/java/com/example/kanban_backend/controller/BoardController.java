package com.example.kanban_backend.controller;

import com.example.kanban_backend.model.BoardEntity;
import com.example.kanban_backend.model.UserEntity;
import com.example.kanban_backend.service.BoardService;
import com.example.kanban_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "https://kanban-project-coral.vercel.app/")
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<BoardEntity> getAllBoards(){
        return boardService.getAllBoards();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BoardEntity>> getBoardsByUserId(@PathVariable Integer userId) {
        List<BoardEntity> boardEntityList = boardService.getBoardByUserId(userId);
        return boardEntityList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok((boardEntityList));
    }

    @PostMapping
    public BoardEntity saveBoard(@RequestBody BoardEntity board) {
        return boardService.saveBoard(board);
    }

    @PostMapping("/newBoards")
    public ResponseEntity<BoardEntity> createBoardsNewUser(@RequestParam String email) {
        Optional<UserEntity> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            BoardEntity board = boardService.insertBoardsNewUser(user.get().getId());
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/updateBoard")
    public ResponseEntity<String> updateName(@RequestBody Map<String, Object> requestData) {
        Integer boardId = (Integer) requestData.get("boardId");
        String nameBoard = (String) requestData.get("name");

        try {
            boardService.updateBoardName(boardId, nameBoard);
            return ResponseEntity.ok("Board editada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error editando board");
        }
    }

    @PostMapping("/delete/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Integer boardId) {
        try {
            boardService.deleteBoard(boardId);
            return ResponseEntity.ok("Board eliminada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error eliminando board");
        }
    }

}
