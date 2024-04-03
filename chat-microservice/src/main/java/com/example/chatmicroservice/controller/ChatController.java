package com.example.chatmicroservice.controller;

import com.example.chatmicroservice.config.UrlConfig;
import com.example.chatmicroservice.dto.MessageDTO;
import com.example.chatmicroservice.dto.TypingDTO;
import com.example.chatmicroservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    private final UrlConfig urlConfig;

    @PostMapping("/admin")
    public ResponseEntity<?> sendMessageToAdmin(@RequestBody String message, @RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
        chatService.sendMessageToAdmin(idUser, message, request, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/admin/seen")
    public ResponseEntity<?> sendVisibilityToAdmin(@RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
        chatService.sendVisibilityToAdmin(idUser, request, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/admin/typing")
    public ResponseEntity<?> sendTypingToAdmin(@RequestBody boolean status, @RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
        chatService.sendTypingToAdmin(idUser, status, request, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<?> sendMessageToUser(@RequestBody MessageDTO messageDTO, @RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);

        chatService.sendMessageToUser(messageDTO.getIdSender(), messageDTO.getMessage(), request, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/user/seen")
    public ResponseEntity<?> sendVisibilityToUser(@RequestBody Long userId, @RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
        chatService.sendVisibilityToUser(userId, request, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/user/typing")
    public ResponseEntity<?> sendTypingToUser(@RequestBody TypingDTO typingDTO, @RequestHeader("Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        Long idUser = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/token", request, Long.class);
        chatService.sendTypingToUser(typingDTO.getUserId(), typingDTO.isStatus(), request, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
