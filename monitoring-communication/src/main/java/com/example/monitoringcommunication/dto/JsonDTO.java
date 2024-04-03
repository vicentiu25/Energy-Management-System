package com.example.monitoringcommunication.dto;
import lombok.Data;

@Data
public class JsonDTO {
    private Long device_id;
    private Double measurement_value;
    private String timestamp;
}
