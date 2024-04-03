package com.example.monitoringcommunication.service;

import com.example.monitoringcommunication.converter.DeviceConverter;
import com.example.monitoringcommunication.dto.DeviceDTO;
import com.example.monitoringcommunication.model.Device;
import com.example.monitoringcommunication.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public Device saveDevice(DeviceDTO deviceDTO) {
        Device device = DeviceConverter.convertFromDTOToEntity(deviceDTO);
        return deviceRepository.save(device);
    }

    public void deleteDevice(Long deviceId){
        Optional<Device> device = deviceRepository.findById(deviceId);
        if(device.isPresent()){
            deviceRepository.deleteById(deviceId);
        }
    }

    public void mapDevice(DeviceDTO deviceDTO) {
        deviceRepository.mapDevice(deviceDTO.getIdDevice(), deviceDTO.getUserId());
    }
    public void mapDeleteDevice(DeviceDTO deviceDTO) {
        deviceRepository.mapDeleteDevice(deviceDTO.getIdDevice());
    }

}
