package com.example.devicemanagmentenergysystem.dto;

import lombok.Data;

@Data
public class DeviceDTO {

    private Long idDevice;
    private String description;
    private String address;
    private Double maxConsumption;
    private Long userId;
}
