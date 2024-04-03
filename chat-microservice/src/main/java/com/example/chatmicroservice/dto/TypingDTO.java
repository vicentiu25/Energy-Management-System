package com.example.chatmicroservice.dto;

import lombok.Data;

@Data
public class TypingDTO {
    private boolean status;
    private Long userId;
}
