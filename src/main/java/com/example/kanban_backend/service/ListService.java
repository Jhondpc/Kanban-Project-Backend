package com.example.kanban_backend.service;

import com.example.kanban_backend.model.BoardEntity;
import com.example.kanban_backend.model.ListEntity;
import com.example.kanban_backend.repository.ListRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListService {

    @Autowired
    private ListRepository listRepository;

    public List<ListEntity> getAllList() {
        return listRepository.findAll();
    }

    public ListEntity saveList(ListEntity list) {
        return listRepository.save(list);
    }

    @Transactional
    public void moveCardBetweenLists(Integer sourceListId, Integer destinationListId, Integer sourcePosition, Integer dstPosition, Integer cardId, String cardName) {

        listRepository.deleteCardSourceList(sourceListId, sourcePosition);
        listRepository.orderCardsSourceList(sourceListId, sourcePosition);

        listRepository.updateDestinationList(destinationListId, dstPosition);
        listRepository.insertDestinationList(cardId, cardName, destinationListId, dstPosition);
    }
}
