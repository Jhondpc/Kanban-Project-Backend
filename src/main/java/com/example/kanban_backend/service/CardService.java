package com.example.kanban_backend.service;

import com.example.kanban_backend.model.CardEntity;
import com.example.kanban_backend.model.ListEntity;
import com.example.kanban_backend.repository.CardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    public List<CardEntity> getAllCards() {
        return cardRepository.findAll();
    }

    public CardEntity saveCard(CardEntity card) {
        return cardRepository.save(card);
    }

    public Integer findMaxPositionByList(Integer listId){ return cardRepository.findMaxPositionByList(listId); }

    @Transactional
    public void updateNameCard(Integer cardId, String cardName) {

        cardRepository.updateNameCard(cardId, cardName);
    }
}
