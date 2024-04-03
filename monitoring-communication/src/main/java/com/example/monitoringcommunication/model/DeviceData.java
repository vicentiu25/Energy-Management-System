package com.example.monitoringcommunication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "DEVICEDATA_TABLE")
@Data
public class DeviceData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DATA")
    private Long idData;

    @ManyToOne()
    @JoinColumn(name = "ID_DEVICE")
    private Device idDevice;

    @Column(name = "CONSUMPTION", nullable = false)
    private Double consumption;

    @Column(name = "TIMESTAMP", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "NO_MEASUREMENTS", nullable = false)
    private Long noMeasurements;
}
