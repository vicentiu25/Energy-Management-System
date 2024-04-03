package com.example.monitoringcommunication.controller;

import com.example.monitoringcommunication.config.UrlConfig;
import com.example.monitoringcommunication.dto.DeviceDataDTO;
import com.example.monitoringcommunication.service.DeviceDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/devicedata/")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class DeviceDataController {
    private final DeviceDataService deviceDataService;
    private final UrlConfig urlConfig;

    @GetMapping("/getByDevice")
    public ResponseEntity<List<DeviceDataDTO>> getAllDeviceDataByUser(@RequestParam Long userId, @RequestParam String date, @RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
        return ResponseEntity.ok(deviceDataService.getAllDeviceDataByUser(userId, date));
    }
}
