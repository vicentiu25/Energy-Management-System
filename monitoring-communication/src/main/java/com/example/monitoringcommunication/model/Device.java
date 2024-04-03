package com.example.monitoringcommunication.model;

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

    @Column(name = "MAXCONSUMPTION", nullable = false)
    private Double maxConsumption;

    @Column(name = "USERID")
    private Long userId;
}