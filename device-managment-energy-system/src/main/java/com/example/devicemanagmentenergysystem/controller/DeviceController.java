package com.example.devicemanagmentenergysystem.controller;

import com.example.devicemanagmentenergysystem.config.UrlConfig;
import com.example.devicemanagmentenergysystem.converter.DeviceConverter;
import com.example.devicemanagmentenergysystem.dto.DeviceDTO;
import com.example.devicemanagmentenergysystem.exception.BusinessException;
import com.example.devicemanagmentenergysystem.model.Device;
import com.example.devicemanagmentenergysystem.model.User;
import com.example.devicemanagmentenergysystem.service.DeviceService;
import com.example.devicemanagmentenergysystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/device/")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;
    private final UserService userService;
    private final UrlConfig urlConfig;

    @PostMapping("/create")
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceDTO deviceDTO, @RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
        deviceDTO.setUserId(null);
        Device device = deviceService.saveDevice(deviceDTO);
        new RestTemplate().postForObject("http://monitoring-communication:8083/device/create", DeviceConverter.convertFromEntityToDTO(device), DeviceDTO.class);
        return ResponseEntity.ok(DeviceConverter.convertFromEntityToDTO(device));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DeviceDTO>> getAll(@RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @GetMapping("/getByUser")
    public ResponseEntity<List<DeviceDTO>> getAllDevicesByUser(@RequestParam Long userId, @RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
        return ResponseEntity.ok(deviceService.getAllByUser(userId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDevice(@RequestBody DeviceDTO deviceDTO, @RequestHeader("Authorization") String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<?> request = new HttpEntity<>(headers);
            Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
            Device device = deviceService.updateDevice(deviceDTO);
            return ResponseEntity.ok(DeviceConverter.convertFromEntityToDTO(device));
        } catch (BusinessException businessException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessException.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDevice(@RequestParam Long deviceId, @RequestHeader("Authorization") String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<?> request = new HttpEntity<>(headers);
            Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
            deviceService.deleteDevice(deviceId);
            new RestTemplate().postForObject("http://monitoring-communication:8083/device/delete?deviceId={deviceId}", request, Long.class, deviceId);
            return ResponseEntity.ok(deviceId);
        } catch (BusinessException businessException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessException.getMessage());
        }
    }

    @PatchMapping("/map/create")
    public ResponseEntity<?> createMapping(@RequestBody DeviceDTO deviceDTO, @RequestHeader("Authorization") String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<?> request = new HttpEntity<>(headers);
            Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
            User user = userService.findUserById(deviceDTO.getUserId());
            deviceService.mapDevice(deviceDTO);
            HttpEntity<?> request2 = new HttpEntity<>(deviceDTO, headers);
            new RestTemplate().postForObject("http://monitoring-communication:8083/device/map/create", request2, DeviceDTO.class);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (BusinessException businessException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessException.getMessage());
        }
    }

    @PatchMapping("/map/delete")
    public ResponseEntity<?> deleteMapping(@RequestBody DeviceDTO deviceDTO, @RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
        deviceService.mapDeleteDevice(deviceDTO);
        HttpEntity<?> request2 = new HttpEntity<>(deviceDTO, headers);
        new RestTemplate().postForObject("http://monitoring-communication:8083/device/map/delete", request2, DeviceDTO.class);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
