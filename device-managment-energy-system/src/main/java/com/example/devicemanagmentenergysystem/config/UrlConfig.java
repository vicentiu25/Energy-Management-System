package com.example.devicemanagmentenergysystem.config;

import org.springframework.stereotype.Component;

@Component
public class UrlConfig {
    private final String userMicroserviceUrl = "http://user-management:8081/";

    public String getUserMicroserviceUrl() {
        return this.userMicroserviceUrl;
    }
}
