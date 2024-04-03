package com.example.monitoringcommunication.converter;

import com.example.monitoringcommunication.dto.DeviceDataDTO;
import com.example.monitoringcommunication.dto.JsonDTO;
import com.example.monitoringcommunication.model.Device;
import com.example.monitoringcommunication.model.DeviceData;
import com.example.monitoringcommunication.repository.DeviceRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class DeviceDataConverter {
    public static DeviceDataDTO convertFromJsonToDTO(JsonDTO jsonDTO) {
        DeviceDataDTO deviceDataDTO = new DeviceDataDTO();
        deviceDataDTO.setIdDevice(jsonDTO.getDevice_id());
        deviceDataDTO.setConsumption(jsonDTO.getMeasurement_value());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime timestamp = LocalDateTime.parse(jsonDTO.getTimestamp(), formatter);
        deviceDataDTO.setTimestamp(timestamp);
        return deviceDataDTO;
    }

    public static DeviceData convertFromDTOToEntity(DeviceDataDTO deviceDataDTO, Device device) {
        DeviceData deviceData = new DeviceData();
        deviceData.setIdDevice(device);
        deviceData.setIdData(deviceDataDTO.getIdData());
        deviceData.setConsumption(deviceDataDTO.getConsumption());
        deviceData.setTimestamp(deviceDataDTO.getTimestamp());
        deviceData.setNoMeasurements(deviceDataDTO.getNoMeasurements());
        return deviceData;
    }

    public static DeviceDataDTO convertFromEntityToDTO(DeviceData deviceData) {
        DeviceDataDTO deviceDataDTO = new DeviceDataDTO();
        deviceDataDTO.setIdData(deviceData.getIdData());
        deviceDataDTO.setIdDevice(deviceData.getIdDevice().getIdDevice());
        deviceDataDTO.setIdData(deviceData.getIdData());
        deviceDataDTO.setConsumption(deviceData.getConsumption());
        deviceDataDTO.setTimestamp(deviceData.getTimestamp());
        deviceDataDTO.setNoMeasurements(deviceData.getNoMeasurements());
        return deviceDataDTO;
    }


    public static List<DeviceDataDTO> convertEntitiesToDTOs(List<DeviceData> deviceDataList) {
        return deviceDataList.stream()
                .map(DeviceDataConverter::convertFromEntityToDTO)
                .collect(Collectors.toList());
    }
}
