package com.example.monitoringcommunication.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeviceDataDTO {
    private Long idData;
    private Long idDevice;
    private Double consumption;
    private LocalDateTime timestamp;
    private Long noMeasurements;
}
