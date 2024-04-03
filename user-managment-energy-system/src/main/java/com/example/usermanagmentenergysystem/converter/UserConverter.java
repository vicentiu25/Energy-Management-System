package com.example.usermanagmentenergysystem.converter;

import com.example.usermanagmentenergysystem.dto.UserDTO;
import com.example.usermanagmentenergysystem.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
    public static User convertFromDTOToEntity(UserDTO userDTO) {
        User user = new User();
        user.setIdUser(userDTO.getIdUser());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return user;
    }
    public static UserDTO convertFromEntityToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(user.getIdUser());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
    public static List<UserDTO> convertEntitiesToDTOs(List<User> users) {
        return users.stream()
                .map(UserConverter::convertFromEntityToDTO)
                .collect(Collectors.toList());
    }
}
