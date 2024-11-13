package com.example.kanban_backend.service;

import com.example.kanban_backend.model.BoardEntity;
import com.example.kanban_backend.model.UserEntity;
import com.example.kanban_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> validUser(String email, String password) {
        return userRepository.validUser(email, password);
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Transactional
    public void updateActiveBoard(Integer activeBoard, Integer userId) {
        userRepository.updateActiveBoard(activeBoard, userId);
    }
}
