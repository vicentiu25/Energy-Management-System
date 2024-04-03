package com.example.devicemanagmentenergysystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USER_TABLE")
@Data
public class User {
    @Id
    @Column(name = "ID_USER")
    private Long idUser;
}
