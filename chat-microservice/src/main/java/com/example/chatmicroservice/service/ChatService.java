package com.example.chatmicroservice.service;

import com.example.chatmicroservice.config.UrlConfig;
import com.example.chatmicroservice.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final SimpMessagingTemplate messagingTemplate;
    private final UrlConfig urlConfig;


    public void sendMessageToAdmin(Long idSender, String message, HttpEntity request, String token) {

        Long adminId = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/admin", request, Long.class);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(token);
        headerAccessor.setLeaveMutable(true);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage(message);
        messageDTO.setIdSender(idSender);
        messagingTemplate.convertAndSendToUser(String.valueOf(adminId), "/chat", messageDTO, headerAccessor.getMessageHeaders());
    }

    public void sendVisibilityToAdmin(Long idSender, HttpEntity request, String token) {
        Long adminId = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/admin", request, Long.class);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(token);
        headerAccessor.setLeaveMutable(true);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage("");
        messageDTO.setIdSender(idSender);
        messageDTO.setSeen(true);
        messagingTemplate.convertAndSendToUser(String.valueOf(adminId), "/chat", messageDTO, headerAccessor.getMessageHeaders());
    }

    public void sendTypingToAdmin(Long idSender, boolean status, HttpEntity request, String token) {
        Long adminId = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/admin", request, Long.class);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(token);
        headerAccessor.setLeaveMutable(true);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage("");
        messageDTO.setIdSender(idSender);
        messageDTO.setTyping(status);
        messageDTO.setSeen(false);
        messagingTemplate.convertAndSendToUser(String.valueOf(adminId), "/chat", messageDTO, headerAccessor.getMessageHeaders());
    }


    public void sendMessageToUser(Long idSender, String message, HttpEntity request, String token) {
        Long adminId = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/admin", request, Long.class);

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(token);
        headerAccessor.setLeaveMutable(true);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage(message);
        messageDTO.setIdSender(adminId);
        messagingTemplate.convertAndSendToUser(String.valueOf(idSender), "/chat", messageDTO, headerAccessor.getMessageHeaders());
    }

    public void sendVisibilityToUser(Long idSender, HttpEntity request, String token) {
        Long adminId = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/admin", request, Long.class);

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(token);
        headerAccessor.setLeaveMutable(true);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage("");
        messageDTO.setIdSender(adminId);
        messageDTO.setSeen(true);
        messagingTemplate.convertAndSendToUser(String.valueOf(idSender), "/chat", messageDTO, headerAccessor.getMessageHeaders());
    }

    public void sendTypingToUser(Long idSender, boolean status, HttpEntity request, String token) {
        Long adminId = new RestTemplate().postForObject(urlConfig.getUserMicroserviceUrl() + "user/admin", request, Long.class);

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(token);
        headerAccessor.setLeaveMutable(true);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage("");
        messageDTO.setIdSender(adminId);
        messageDTO.setSeen(false);
        messageDTO.setTyping(status);
        messagingTemplate.convertAndSendToUser(String.valueOf(idSender), "/chat", messageDTO, headerAccessor.getMessageHeaders());
    }
}
