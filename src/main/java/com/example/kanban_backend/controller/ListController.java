package com.example.kanban_backend.controller;

import com.example.kanban_backend.model.BoardEntity;
import com.example.kanban_backend.model.ListEntity;
import com.example.kanban_backend.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "https://kanban-project-coral.vercel.app/")
@RestController
@RequestMapping("/api/lists")
public class ListController {

    @Autowired
    private ListService listService;

    @GetMapping
    public List<ListEntity> getAllList(){
        return listService.getAllList();
    }

    @PostMapping
    public ListEntity saveList(@RequestBody ListEntity list) {
        return listService.saveList(list);
    }

    @PostMapping("/updateLists")
    public ResponseEntity<String> updateLists(@RequestBody Map<String, Object> requestData) {
        Integer sourceListId = (Integer) requestData.get("sourceListId");
        Integer destinationListId = (Integer) requestData.get("destinationListId");
        Integer sourcePosition = (Integer) requestData.get("sourcePosition");
        Integer dstPosition = (Integer) requestData.get("dstPosition");
        Integer cardId = (Integer) requestData.get("cardId");
        String cardName = (String) requestData.get("cardName");

        if (sourceListId == null || destinationListId == null || sourcePosition == null || dstPosition == null || cardId == null) {
            return ResponseEntity.badRequest().body("Faltan parámetros");
        }

        try {
            listService.moveCardBetweenLists(sourceListId, destinationListId, sourcePosition, dstPosition, cardId, cardName);
            return ResponseEntity.ok("Tarjeta movida exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error moviendo tarjeta");
        }
    }
}
