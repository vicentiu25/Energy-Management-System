package com.example.devicemanagmentenergysystem.controller;

import com.example.devicemanagmentenergysystem.config.UrlConfig;
import com.example.devicemanagmentenergysystem.dto.UserDTO;
import com.example.devicemanagmentenergysystem.exception.BusinessException;
import com.example.devicemanagmentenergysystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UrlConfig urlConfig;

    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestParam Long userId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(userId);
        userService.saveUser(userDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId, @RequestHeader("Authorization") String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<?> request = new HttpEntity<>(headers);
            Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
            userService.deleteUser(userId);
            return ResponseEntity.ok(userId);
        } catch (BusinessException businessException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessException.getMessage());
        }
    }


}
