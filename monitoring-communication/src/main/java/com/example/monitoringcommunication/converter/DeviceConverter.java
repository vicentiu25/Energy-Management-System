package com.example.monitoringcommunication.converter;

import com.example.monitoringcommunication.dto.DeviceDTO;
import com.example.monitoringcommunication.model.Device;

public class DeviceConverter {
    public static Device convertFromDTOToEntity(DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setIdDevice(deviceDTO.getIdDevice());
        device.setMaxConsumption(deviceDTO.getMaxConsumption());
        device.setIdDevice(deviceDTO.getIdDevice());
        return device;
    }

    public static DeviceDTO convertFromEntityToDTO(Device device) {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setIdDevice(device.getIdDevice());
        deviceDTO.setMaxConsumption(device.getMaxConsumption());
        deviceDTO.setUserId(device.getUserId());
        return deviceDTO;
    }
}
