package com.example.kanban_backend.model;

import lombok.Data;

@Data
public class UserDTO {

    private String name;
    private String email;
    private String password;

    public UserDTO() {}

    public UserDTO(Long id, String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
