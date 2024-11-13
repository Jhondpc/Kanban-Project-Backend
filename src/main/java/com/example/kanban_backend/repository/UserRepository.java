package com.example.kanban_backend.repository;

import com.example.kanban_backend.model.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);

    @Query(nativeQuery = true, value = "select * from users where email = ?1 and password = ?2")
    Optional<UserEntity> validUser (String email, String password);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users SET active_board = ?1 WHERE id = ?2")
    void updateActiveBoard(Integer activeBoard, Integer userId);
}
