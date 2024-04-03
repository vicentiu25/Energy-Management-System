package com.example.usermanagmentenergysystem.model;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;


@Entity
@Table(name = "USER_TABLE")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER")
    private Long idUser;

    @NotBlank(message = "username should not be blank")
    @Size(min = 3, message = "username should be at least 3 chars")
    @Size(max = 15, message = "username should be at most 15 chars")
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", length = 64, nullable = false)
    private String password;

    @Column(name = "ROLE", nullable = false)
    private Role role;
}
