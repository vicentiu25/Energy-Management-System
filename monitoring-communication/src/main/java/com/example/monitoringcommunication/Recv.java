package com.example.monitoringcommunication;

import com.example.monitoringcommunication.converter.DeviceDataConverter;
import com.example.monitoringcommunication.dto.DeviceDataDTO;
import com.example.monitoringcommunication.dto.JsonDTO;
import com.example.monitoringcommunication.service.DeviceDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class Recv {
    private final DeviceDataService deviceDataService;
    private final static String QUEUE_NAME = "device_queue";

    @RabbitListener(queues = QUEUE_NAME)
    public void receiveMessage(byte[] message) {
        String receivedMessage = new String(message, StandardCharsets.UTF_8);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonDTO jsonDTO = mapper.readValue(receivedMessage, JsonDTO.class);
            DeviceDataDTO deviceDataDTO = DeviceDataConverter.convertFromJsonToDTO(jsonDTO);
            deviceDataService.saveDeviceData(deviceDataDTO);
            System.out.println("Received message: " + receivedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}