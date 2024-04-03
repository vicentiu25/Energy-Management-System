package com.example.chatmicroservice.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private Long idSender;
    private String message;
    private boolean seen;
    private boolean isTyping;
}
