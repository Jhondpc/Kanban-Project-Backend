package com.example.kanban_backend.controller;

import com.example.kanban_backend.model.BoardEntity;
import com.example.kanban_backend.model.UserEntity;
import com.example.kanban_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://kanban-project-coral.vercel.app/")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserEntity> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserEntity user) {
        Optional<UserEntity> userEntity = userService.getUserByEmail(user.getEmail());
        if (userEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya está registrado.");
        }

        UserEntity newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/login")
    public ResponseEntity<?> validaUser(@RequestParam String email, @RequestParam String password) {
        Optional<UserEntity> user = userService.validUser(email, password);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        Optional<UserEntity> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/activeBoard")
    public ResponseEntity<String> updateLists(@RequestBody Map<String, Object> requestData) {
        Integer activeBoard = (Integer) requestData.get("activeBoard");
        Integer userId = (Integer) requestData.get("userId");

        if (activeBoard == null || userId == null) {
            return ResponseEntity.badRequest().body("Faltan parámetros.");
        }

        try {
            userService.updateActiveBoard(activeBoard, userId);
            return ResponseEntity.ok("Active board actuaizada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar active board.");
        }
    }
}
