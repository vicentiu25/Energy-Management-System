package com.example.devicemanagmentenergysystem.converter;

import com.example.devicemanagmentenergysystem.dto.UserDTO;
import com.example.devicemanagmentenergysystem.model.User;

public class UserConverter {
    public static UserDTO convertFromEntityToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(user.getIdUser());
        return userDTO;
    }
    public static User convertFromDTOToEntity(UserDTO userDTO) {
        User user = new User();
        user.setIdUser(userDTO.getIdUser());
        return user;
    }
}
