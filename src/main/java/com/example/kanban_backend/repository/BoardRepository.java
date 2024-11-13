package com.example.kanban_backend.repository;

import com.example.kanban_backend.model.BoardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    List<BoardEntity> findByUserId(Integer id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO `kanban_db`.`boards` (`color`, `name`, `users_id`) VALUES ('#579DFF', 'Primer Tablero', ?1);\n")
    BoardEntity insertBoardsNewUser(Integer userId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE boards SET name = ?2 WHERE id = ?1")
    void updateBoardName(Integer boardId, String newBoardName);

}
