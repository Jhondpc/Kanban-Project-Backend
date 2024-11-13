package com.example.kanban_backend.controller;

import com.example.kanban_backend.model.CardDTO;
import com.example.kanban_backend.model.CardEntity;
import com.example.kanban_backend.model.ListEntity;
import com.example.kanban_backend.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "https://kanban-project-coral.vercel.app/")
@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping
    public List<CardEntity> getAllCards(){
        return cardService.getAllCards();
    }

    @PostMapping
    public CardEntity saveCard(@RequestBody CardDTO cardDTO) {

        Integer maxPosition = cardService.findMaxPositionByList(cardDTO.getList().getId());
        int newPosition = (maxPosition == null) ? 1 : maxPosition + 1;

        CardEntity card = new CardEntity();
        card.setName(cardDTO.getName());
        card.setList(cardDTO.getList());
        card.setPosition(newPosition);
        return cardService.saveCard(card);
    }

    @PostMapping("/updateCard")
    public ResponseEntity<String> updateName(@RequestBody Map<String, Object> requestData) {
        Integer cardId = (Integer) requestData.get("cardId");
        String newNameCard = (String) requestData.get("title");

        try {
            cardService.updateNameCard(cardId, newNameCard);
            return ResponseEntity.ok("Tarjeta editada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error editando tarjeta");
        }
    }

}
