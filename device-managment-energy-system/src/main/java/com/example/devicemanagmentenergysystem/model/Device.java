package com.example.devicemanagmentenergysystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "DEVICE_TABLE")
@Data
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DEVICE")
    private Long idDevice;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "MAXCONSUMPTION", nullable = false)
    private Double maxConsumption;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_USER")
    private User user;
}
