package com.example.devicemanagmentenergysystem.converter;

import com.example.devicemanagmentenergysystem.dto.DeviceDTO;
import com.example.devicemanagmentenergysystem.model.Device;
import com.example.devicemanagmentenergysystem.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class DeviceConverter {
    public static DeviceDTO convertFromEntityToDTO(Device device) {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setIdDevice(device.getIdDevice());
        deviceDTO.setAddress(device.getAddress());
        deviceDTO.setDescription(device.getDescription());
        deviceDTO.setMaxConsumption(device.getMaxConsumption());
        if(device.getUser() != null) deviceDTO.setUserId(device.getUser().getIdUser());
        return deviceDTO;
    }

    public static Device convertFromDTOToEntity(DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setIdDevice(deviceDTO.getIdDevice());
        device.setDescription(deviceDTO.getDescription());
        device.setAddress(deviceDTO.getAddress());
        device.setMaxConsumption(deviceDTO.getMaxConsumption());
        if(deviceDTO.getUserId() != null){
            User user = new User();
            user.setIdUser(deviceDTO.getUserId());
            device.setUser(user);
        }
        return device;
    }

    public static List<DeviceDTO> convertEntitiesToDTOs(List<Device> devices) {
        return devices.stream()
                .map(DeviceConverter::convertFromEntityToDTO)
                .collect(Collectors.toList());
    }
}
