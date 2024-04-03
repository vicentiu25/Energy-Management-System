package com.example.devicemanagmentenergysystem.service;

import com.example.devicemanagmentenergysystem.converter.DeviceConverter;
import com.example.devicemanagmentenergysystem.dto.DeviceDTO;
import com.example.devicemanagmentenergysystem.exception.BusinessException;
import com.example.devicemanagmentenergysystem.model.Device;
import com.example.devicemanagmentenergysystem.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public Device saveDevice(DeviceDTO deviceDTO) {
        Device device = DeviceConverter.convertFromDTOToEntity(deviceDTO);
        return deviceRepository.save(device);
    }

    public List<DeviceDTO> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        return DeviceConverter.convertEntitiesToDTOs(devices);
    }

    public List<DeviceDTO> getAllByUser(Long userId) {
        List<Device> devices = deviceRepository.findAllByUser(userId);
        return DeviceConverter.convertEntitiesToDTOs(devices);
    }

    public Device updateDevice(DeviceDTO deviceDTO) throws BusinessException {
        Optional<Device> device = deviceRepository.findById(deviceDTO.getIdDevice());

        if(device.isEmpty())
            throw new BusinessException("This device does not exist");
        else {
            return deviceRepository.save(DeviceConverter.convertFromDTOToEntity(deviceDTO));
        }
    }

    public void deleteDevice(Long deviceId) throws BusinessException{
        Optional<Device> device = deviceRepository.findById(deviceId);
        if(device.isEmpty()){
            throw new BusinessException("This device does not exist");
        } else {
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
