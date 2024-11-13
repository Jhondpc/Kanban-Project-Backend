package com.example.kanban_backend.repository;

import com.example.kanban_backend.model.CardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Integer> {
    @Query(nativeQuery = true, value = "SELECT MAX(c.position) FROM cards c WHERE c.lists_id = ?1")
    Integer findMaxPositionByList(Integer listId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE cards SET name = ?2 WHERE id = ?1")
    void updateNameCard(Integer cardId, String newNameCard);
}
