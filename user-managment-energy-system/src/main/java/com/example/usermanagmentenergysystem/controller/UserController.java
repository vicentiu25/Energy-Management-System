package com.example.usermanagmentenergysystem.controller;

import com.example.usermanagmentenergysystem.authentication.JWTokenCreator;
import com.example.usermanagmentenergysystem.converter.UserConverter;
import com.example.usermanagmentenergysystem.dto.TokenRespDTO;
import com.example.usermanagmentenergysystem.dto.UserDTO;
import com.example.usermanagmentenergysystem.exception.BusinessException;
import com.example.usermanagmentenergysystem.model.User;
import com.example.usermanagmentenergysystem.service.UserService;
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
    private final JWTokenCreator jwTokenCreator;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long idUser = jwTokenCreator.getUserIdFromToken(jwtToken);
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<?> save(@RequestBody UserDTO userDTO)
            throws UnsupportedEncodingException {
        try {
            User user = userService.saveUser(userDTO);
            new RestTemplate().postForObject("http://device-management:8082/user/create?userId={userId}", null, UserDTO.class, user.getIdUser());
            return ResponseEntity.ok(userDTO);
        } catch (BusinessException businessException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessException.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        try {
            String token = userService.matchUser(userDTO.getUsername(), userDTO.getPassword());
            return ResponseEntity.ok(new TokenRespDTO(token));
        } catch (BusinessException businessException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessException.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, @RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            Long idUser = jwTokenCreator.getUserIdFromToken(jwtToken);
            User user = userService.updateUser(userDTO);
            return ResponseEntity.ok(UserConverter.convertFromEntityToDTO(user));
        } catch (BusinessException businessException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessException.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId, @RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            Long idUser = jwTokenCreator.getUserIdFromToken(jwtToken);
            userService.deleteUser(userId);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<?> request = new HttpEntity<>(headers);
            new RestTemplate().postForObject("http://device-management:8082/user/delete?userId={userId}", request, Long.class, userId);
            return ResponseEntity.ok(userId);
        } catch (BusinessException businessException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessException.getMessage());
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<?> getAdminId(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long idUser = jwTokenCreator.getUserIdFromToken(jwtToken);
        return ResponseEntity.ok(userService.getAdminId());
    }

    @PostMapping("/token")
    public ResponseEntity<?> getIdFromToken(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long idUser = jwTokenCreator.getUserIdFromToken(jwtToken);
        return ResponseEntity.ok(idUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        jwTokenCreator.invalidateToken(jwtToken);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
