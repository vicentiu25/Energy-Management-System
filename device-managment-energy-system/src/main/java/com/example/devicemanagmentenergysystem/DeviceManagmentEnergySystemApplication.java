package com.example.devicemanagmentenergysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class DeviceManagmentEnergySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceManagmentEnergySystemApplication.class, args);
	}
}
