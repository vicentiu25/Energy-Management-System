package com.example.usermanagmentenergysystem.dto;

import com.example.usermanagmentenergysystem.model.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long idUser;
    private String username;
    private String password;
    private Role role;
}
