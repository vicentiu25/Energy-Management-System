package com.example.monitoringcommunication.controller;

import com.example.monitoringcommunication.config.UrlConfig;
import com.example.monitoringcommunication.converter.DeviceConverter;
import com.example.monitoringcommunication.dto.DeviceDTO;
import com.example.monitoringcommunication.model.Device;
import com.example.monitoringcommunication.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/device/")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;
    private final UrlConfig urlConfig;

    @PostMapping("/create")
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceDTO deviceDTO, @RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
        Device device = deviceService.saveDevice(deviceDTO);
        return ResponseEntity.ok(DeviceConverter.convertFromEntityToDTO(device));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteDevice(@RequestParam Long deviceId, @RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
        deviceService.deleteDevice(deviceId);
        return ResponseEntity.ok(deviceId);
    }

    @PostMapping("/map/create")
    public ResponseEntity<?> createMapping(@RequestBody DeviceDTO deviceDTO, @RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
        deviceService.mapDevice(deviceDTO);
        return ResponseEntity.ok(deviceDTO);
    }

    @PostMapping("/map/delete")
    public ResponseEntity<?> deleteMapping(@RequestBody DeviceDTO deviceDTO, @RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
        deviceService.mapDeleteDevice(deviceDTO);
        return ResponseEntity.ok(deviceDTO);
    }
}
