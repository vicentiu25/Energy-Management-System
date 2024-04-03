package com.example.monitoringcommunication.service;

import com.example.monitoringcommunication.converter.DeviceConverter;
import com.example.monitoringcommunication.converter.DeviceDataConverter;
import com.example.monitoringcommunication.dto.DeviceDTO;
import com.example.monitoringcommunication.dto.DeviceDataDTO;
import com.example.monitoringcommunication.model.Device;
import com.example.monitoringcommunication.model.DeviceData;
import com.example.monitoringcommunication.repository.DeviceDataRepository;
import com.example.monitoringcommunication.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceDataService {
    private final DeviceDataRepository deviceDataRepository;
    private final DeviceRepository deviceRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public DeviceData saveDeviceData(DeviceDataDTO deviceDataDTO) {
        Device device = deviceRepository.findById(deviceDataDTO.getIdDevice()).get();
        DeviceData deviceData = DeviceDataConverter.convertFromDTOToEntity(deviceDataDTO, device);
        deviceData.setTimestamp(deviceData.getTimestamp()
                                                .withMinute(0)
                                                .withSecond(0)
                                                .withNano(0));

        DeviceData findDeviceData = deviceDataRepository.findDeviceData(deviceData.getTimestamp(), deviceData.getIdDevice().getIdDevice());
        if(findDeviceData != null){
            Long noMeasurements = findDeviceData.getNoMeasurements();
            Double oldConsumption = findDeviceData.getConsumption();
            findDeviceData.setConsumption((findDeviceData.getConsumption() * noMeasurements + deviceData.getConsumption()) / (noMeasurements + 1));
            findDeviceData.setNoMeasurements(noMeasurements + 1);
            checkHourlyEnergyData(device.getIdDevice(), findDeviceData.getConsumption(), oldConsumption);
            return deviceDataRepository.save(findDeviceData);
        } else {
            deviceData.setNoMeasurements(1L);
            checkHourlyEnergyData(device.getIdDevice(), deviceData.getConsumption(), 0D);
            return deviceDataRepository.save(deviceData);
        }
    }

    public void checkHourlyEnergyData(Long deviceId, Double consumption, Double oldConsumption) {
        Double maxConsumption = deviceRepository.findById(deviceId).get().getMaxConsumption();
        if (consumption > maxConsumption && oldConsumption <= maxConsumption) {
            messagingTemplate.convertAndSend("/queue/socket/data",
                    deviceId);
        }
    }

    public List<DeviceDataDTO> getAllDeviceDataByUser(Long userId, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime timestamp = LocalDateTime.parse(date, formatter);
        List<DeviceData> deviceDatas = deviceDataRepository.findDeviceDataByUser(userId, timestamp, timestamp.plusDays(1));
        return DeviceDataConverter.convertEntitiesToDTOs(deviceDatas);
    }

}
