package com.example.kanban_backend.repository;

import com.example.kanban_backend.model.ListEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends JpaRepository<ListEntity, Integer> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO `kanban_db`.`lists` (`name`, `boards_id`) VALUES ('Listas de tareas', '1');\n" +
            "INSERT INTO `kanban_db`.`lists` (`name`, `boards_id`) VALUES ('En Progreso', '1');\n" +
            "INSERT INTO `kanban_db`.`lists` (`name`, `boards_id`) VALUES ('Hecho', '1');")
    void insertListsNewUser();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM cards WHERE lists_id = ?1 AND position = ?2")
    void deleteCardSourceList(Integer sourceListId, Integer sourcePosition);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE cards SET position = position - 1 WHERE lists_id = ?1 AND position > ?2")
    void orderCardsSourceList(Integer sourceListId, Integer sourcePosition);


    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE cards SET position = position + 1 WHERE lists_id = ?1 AND position >= ?2")
    void updateDestinationList(Integer destinationListId, Integer dstPosition);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO cards (id, name, lists_id, position) VALUES (?1, ?2, ?3, ?4)")
    void insertDestinationList(Integer cardId, String cardName, Integer destinationListId, Integer dstPosition);

}
